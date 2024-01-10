/*
 * C�digo-fonte do livro "Programa��o Java para a Web"
 * Autores: D�cio Heinzelmann Luckow <decioluckow@gmail.com>
 *          Alexandre Altair de Melo <alexandremelo.br@gmail.com>
 *
 * ISBN: 978-85-7522-238-6
 * http://www.javaparaweb.com.br
 * http://www.novatec.com.br/livros/javaparaweb
 * Editora Novatec, 2010 - todos os direitos reservados
 *
 * LICEN�A: Este arquivo-fonte est� sujeito a Atribui��o 2.5 Brasil, da licen�a Creative Commons,
 * que encontra-se dispon�vel no seguinte endere�o URI: http://creativecommons.org/licenses/by/2.5/br/
 * Se voc� n�o recebeu uma c�pia desta licen�a, e n�o conseguiu obt�-la pela internet, por favor,
 * envie uma notifica��o aos seus autores para que eles possam envi�-la para voc� imediatamente.
 *
 *
 * Source-code of "Programa��o Java para a Web" book
 * Authors: D�cio Heinzelmann Luckow <decioluckow@gmail.com>
 *          Alexandre Altair de Melo <alexandremelo.br@gmail.com>
 *
 * ISBN: 978-85-7522-238-6
 * http://www.javaparaweb.com.br
 * http://www.novatec.com.br/livros/javaparaweb
 * Editora Novatec, 2010 - all rights reserved
 *
 * LICENSE: This source file is subject to Attribution version 2.5 Brazil of the Creative Commons
 * license that is available through the following URI:  http://creativecommons.org/licenses/by/2.5/br/
 * If you did not receive a copy of this license and are unable to obtain it through the web, please
 * send a note to the authors so they can mail you a copy immediately.
 *
 */
package br.com.artsoft.finart.controller.rs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.artsoft.finart.controller.util.ContextoUtil;
import br.com.artsoft.finart.model.domain.Categoria;
import br.com.artsoft.finart.model.domain.Conta;
import br.com.artsoft.finart.model.domain.Lancamento;
import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.service.ContextoRN;
import br.com.artsoft.finart.model.service.LancamentoRN;


@RestController
@RequestMapping("/lancamento")
public class LancamentoRS {

	@Autowired
	LancamentoRN lancamentoRN;		
	
	@Autowired
	ContextoRN contextoRN;
	
	private List<Double>		saldos	= new ArrayList<Double>();
	private float				saldoGeral;
	
	private Integer				periodo;	

	@PostMapping
	public ResponseEntity<Lancamento> salvar(@RequestBody Lancamento lancamento) {			
		
		periodo = 1;	
		
		Usuario usuarioLogado = contextoRN.getUsuarioLogado(ContextoUtil.getEmailUsuarioLogado());
		
		lancamento.setUsuario(usuarioLogado);
		lancamento.setConta(contextoRN.getContaAtiva(usuarioLogado));
		
		//RN LB001 - se a descrição não for informada adiciona descrição da Categoria
		if (lancamento.getDescricao() == null || lancamento.getDescricao() == ""){
			if (lancamento.getCategoria().getDescricao() != null){
				lancamento.setDescricao(lancamento.getCategoria().getDescricao());
			}
		}
		
		lancamentoRN.salvar(lancamento);	
		
		return ResponseEntity.ok(lancamento);		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Lancamento> excluir(@PathVariable("id") Integer codigo) {
		
		Lancamento lancamento = lancamentoRN.carregar(codigo);
		
		if(Objects.isNull(lancamento)) {
			return ResponseEntity.notFound().build();
		}
		
		lancamentoRN.excluir(lancamento);	
		return ResponseEntity.ok(lancamento);	
	}

	public List<Lancamento> getLista() {
		
		switch(periodo){
			case 1:
				return getListaMes(); 
				
			case 2:
				return getListaMesAnterior();
				
			case 3:
				return getListaAteHoje();
				
			case 4:
				return getListaFuturos();
				
			case 5:
				return getListaTodos();
				
			default:
				return getListaMes();
		}		
		
	}
	
	public List<Lancamento> getListaTodos() {
		List<Lancamento> lista = null;
		
		Usuario usuarioLogado = contextoRN.getUsuarioLogado(ContextoUtil.getEmailUsuarioLogado());
		
		Conta conta = contextoRN.getContaAtiva(usuarioLogado);

		Calendar dataSaldo = new GregorianCalendar();
		dataSaldo.add(Calendar.MONTH, -1);
		dataSaldo.add(Calendar.DAY_OF_MONTH, -1);

		Calendar inicio = new GregorianCalendar();
		inicio.add(Calendar.MONTH, -1);
		LancamentoRN lancamentoRN = new LancamentoRN();
		saldoGeral = lancamentoRN.saldo(conta, dataSaldo.getTime());
		lista = lancamentoRN.listar(conta, inicio.getTime(), null);

		Categoria categoria = null;
		double saldo = saldoGeral;
		for (Lancamento lancamento : lista) {
			categoria = lancamento.getCategoria();
			saldo = saldo + (lancamento.getValor().floatValue() * categoria.getFator());
			saldos.add(saldo);
		}

		return lista;
	}
	
	public List<Lancamento> getListaAteHoje() {
		List<Lancamento> listaAteHoje = null;

		Usuario usuarioLogado = contextoRN.getUsuarioLogado(ContextoUtil.getEmailUsuarioLogado());			
		Conta conta = contextoRN.getContaAtiva(usuarioLogado);

		Calendar hoje = new GregorianCalendar();

		LancamentoRN lancamentoRN = new LancamentoRN();
		listaAteHoje = lancamentoRN.listar(conta, null, hoje.getTime());

		return listaAteHoje;
	}
   
	public List<Lancamento> getListaFuturos() {
		List<Lancamento> listaFuturos = null;

		Usuario usuarioLogado = contextoRN.getUsuarioLogado(ContextoUtil.getEmailUsuarioLogado());			
		Conta conta = contextoRN.getContaAtiva(usuarioLogado);

		Calendar amanha = new GregorianCalendar();
		amanha.add(Calendar.DAY_OF_MONTH, 1);

		LancamentoRN lancamentoRN = new LancamentoRN();
		listaFuturos = lancamentoRN.listar(conta, amanha.getTime(), null);

		return listaFuturos;
	}
   
	public List<Lancamento> getListaMes() {
		List<Lancamento> listaMes = null;

		Usuario usuarioLogado = contextoRN.getUsuarioLogado(ContextoUtil.getEmailUsuarioLogado());			
		Conta conta = contextoRN.getContaAtiva(usuarioLogado);

		Calendar diaPrimeiroDoMes = new GregorianCalendar();
		Calendar diaUltimoDoMes = new GregorianCalendar();
		diaPrimeiroDoMes.set(Calendar.DAY_OF_MONTH, 1);
		diaUltimoDoMes.set(Calendar.DAY_OF_MONTH, 30);

		Calendar dataSaldo = new GregorianCalendar();
		dataSaldo.add(Calendar.MONTH, -1);
		dataSaldo.add(Calendar.DAY_OF_MONTH, -1);

		LancamentoRN lancamentoRN = new LancamentoRN();
		listaMes = lancamentoRN.listar(conta, diaPrimeiroDoMes.getTime(), diaUltimoDoMes.getTime());
		saldoGeral = lancamentoRN.saldo(conta, dataSaldo.getTime());

		Categoria categoria = null;
		double saldo = saldoGeral;
		for (Lancamento lancamento : listaMes) {
			categoria = lancamento.getCategoria();
			saldo = saldo + (lancamento.getValor().floatValue() * categoria.getFator());
			saldos.add(saldo);
		}

		return listaMes;
	}
   
	public List<Lancamento> getListaMesAnterior() {
		List<Lancamento> listaMesAnterior;

		Usuario usuarioLogado = contextoRN.getUsuarioLogado(ContextoUtil.getEmailUsuarioLogado());			
		Conta conta = contextoRN.getContaAtiva(usuarioLogado);

		Calendar diaPrimeiroDoMes = new GregorianCalendar();
		Calendar diaUltimoDoMes = new GregorianCalendar();
		diaPrimeiroDoMes.add(Calendar.MONTH, -1);
		diaUltimoDoMes.add(Calendar.MONTH, -1);
		diaPrimeiroDoMes.set(Calendar.DAY_OF_MONTH, 1);
		diaUltimoDoMes.set(Calendar.DAY_OF_MONTH, 30);

		LancamentoRN lancamentoRN = new LancamentoRN();
		listaMesAnterior = lancamentoRN.listar(conta, diaPrimeiroDoMes.getTime(), diaUltimoDoMes.getTime());

		return listaMesAnterior;
	}	
	
	/** TODO geracao de relatorio desabilitada
	 
	
	public StreamedContent getArquivoRetorno() {
		FacesContext context = FacesContext.getCurrentInstance();
		ContextoBean contextoBean = ContextoUtil.getContextoBean();
		String usuario = contextoBean.getUsuarioLogado().getLogin();
		String nomeRelatorioJasper = "extrato";
		String nomeRelatorioSaida = usuario + "_extrato";
		LancamentoRN lancamentoRN = new LancamentoRN();
		GregorianCalendar calendario = new GregorianCalendar();
		calendario.setTime(getDataInicialRelatorio());
		calendario.add(Calendar.DAY_OF_MONTH, -1);
		Date dataSaldo = new Date(calendario.getTimeInMillis());
		RelatorioUtil relatorioUtil = new RelatorioUtil();

		HashMap<String, Object> parametrosRelatorio = new HashMap<String, Object>();
		parametrosRelatorio.put("codigoUsuario", contextoBean.getUsuarioLogado().getCodigo());
		parametrosRelatorio.put("numeroConta", contextoBean.getContaAtiva().getConta());
		parametrosRelatorio.put("dataInicial", getDataInicialRelatorio());
		parametrosRelatorio.put("dataFinal", getDataFinalRelatorio());
		parametrosRelatorio.put("saldoAnterior", lancamentoRN.saldo(contextoBean.getContaAtiva(), dataSaldo));

		try {
			arquivoRetorno = relatorioUtil.geraRelatorio(parametrosRelatorio, nomeRelatorioJasper, nomeRelatorioSaida, RelatorioUtil.RELATORIO_PDF);
		} catch (UtilException e) {
			context.addMessage(null, new FacesMessage("N�o foi poss�vel gerar o relat�rio. Erro: " + e.getMessage()));
			return null;
		} 
		return arquivoRetorno;
	}	 */

}