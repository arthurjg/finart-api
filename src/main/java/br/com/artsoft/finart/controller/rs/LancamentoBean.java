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
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.model.StreamedContent;

import br.com.artsoft.finart.controller.rs.util.ContextoUtil;
import br.com.artsoft.finart.controller.rs.util.RelatorioUtil;
import financeiro.model.Categoria;
import financeiro.model.Cheque;
import financeiro.model.ChequeId;
import financeiro.model.Conta;
import financeiro.model.Lancamento;
import financeiro.rn.ChequeRN;
import financeiro.rn.LancamentoRN;
import financeiro.util.RNException;
import financeiro.util.UtilException;


@RestController
public class LancamentoBean {

	private List<Lancamento>	lista;
	private List<Lancamento>	listaAteHoje;
	private List<Lancamento>	listaFuturos;
	private List<Lancamento>	listaMes;
	private List<Lancamento>	listaMesAnterior;
	private List<Double>		saldos	= new ArrayList<Double>();
	private float				saldoGeral;

	private Lancamento			editado	= new Lancamento();
	private Integer				numeroCheque;
	private Integer				periodo;

	private Date				dataInicialRelatorio;
	private Date				dataFinalRelatorio;
	private StreamedContent		arquivoRetorno;
	
	public LancamentoBean() {
		this.novo();
	}

	public void novo() {
		this.editado = new Lancamento();
		this.editado.setData(new Date(System.currentTimeMillis()));
		this.numeroCheque = null;
		this.periodo = 1;
	}

	public void editar() {
		Cheque cheque = this.editado.getCheque();
		if (cheque != null) {
			this.numeroCheque = cheque.getChequeId().getCheque();
		}
	}

	public void salvar() {
		ContextoBean contextoBean = ContextoUtil.getContextoBean();
		this.editado.setUsuario(contextoBean.getUsuarioLogado());
		this.editado.setConta(contextoBean.getContaAtiva());
		
		//RN LB001 - se a descrição não for informada adiciona descrição da Categoria
		if (this.editado.getDescricao() == null || this.editado.getDescricao() == ""){
			if (this.editado.getCategoria().getDescricao() != null){
				this.editado.setDescricao(this.editado.getCategoria().getDescricao());
			}
		}

		ChequeRN chequeRN = new ChequeRN();
		ChequeId chequeId = null;
		if (this.numeroCheque != null) {
			chequeId = new ChequeId();
			chequeId.setConta(contextoBean.getContaAtiva().getConta());
			chequeId.setCheque(this.numeroCheque);
			Cheque cheque = chequeRN.carregar(chequeId);
			FacesContext context = FacesContext.getCurrentInstance();
			if (cheque == null) {
				FacesMessage msg = new FacesMessage("Cheque não cadastrado");
				context.addMessage(null, msg);
				return;
			} else if (cheque.getSituacao() == Cheque.SITUACAO_CHEQUE_CANCELADO) {
				FacesMessage msg = new FacesMessage("Cheque já cancelado");
				context.addMessage(null, msg);
				return;
			} else {
				this.editado.setCheque(cheque);
				chequeRN.baixarCheque(chequeId, this.editado);
			}
		}
		LancamentoRN lancamentoRN = new LancamentoRN();
		lancamentoRN.salvar(this.editado);
		this.novo();
		this.lista = null;
	}

	public void excluir() {
		LancamentoRN lancamentoRN = new LancamentoRN();
		this.editado = lancamentoRN.carregar(this.editado.getLancamento());
		lancamentoRN.excluir(this.editado);
		this.lista = null;
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
		if (this.lista == null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			Conta conta = contextoBean.getContaAtiva();
			
			Calendar dataSaldo = new GregorianCalendar();
			dataSaldo.add(Calendar.MONTH, -1);
			dataSaldo.add(Calendar.DAY_OF_MONTH, -1);

			Calendar inicio = new GregorianCalendar();
			inicio.add(Calendar.MONTH, -1);

			LancamentoRN lancamentoRN = new LancamentoRN();
			this.saldoGeral = lancamentoRN.saldo(conta, dataSaldo.getTime());
			this.lista = lancamentoRN.listar(conta, inicio.getTime(), null);

			Categoria categoria = null;
			double saldo = this.saldoGeral;
			for (Lancamento lancamento : this.lista) {
				categoria = lancamento.getCategoria();
				saldo = saldo + (lancamento.getValor().floatValue() * categoria.getFator());
				this.saldos.add(saldo);
			}
		}
		return this.lista;
	}
	
   public List<Lancamento> getListaAteHoje() {
   	if (this.listaAteHoje == null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			Conta conta = contextoBean.getContaAtiva();

			Calendar hoje = new GregorianCalendar();

			LancamentoRN lancamentoRN = new LancamentoRN();
			this.listaAteHoje = lancamentoRN.listar(conta, null, hoje.getTime());
		}
		return this.listaAteHoje;
   }
   
   public List<Lancamento> getListaFuturos() {
   	if (this.listaFuturos == null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			Conta conta = contextoBean.getContaAtiva();

			Calendar amanha = new GregorianCalendar();
			amanha.add(Calendar.DAY_OF_MONTH, 1);

			LancamentoRN lancamentoRN = new LancamentoRN();
			this.listaFuturos = lancamentoRN.listar(conta, amanha.getTime(), null);
		}
		return this.listaFuturos;
   }
   
   public List<Lancamento> getListaMes() {
	   	if (this.listaMes == null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			Conta conta = contextoBean.getContaAtiva();

			Calendar diaPrimeiroDoMes = new GregorianCalendar();
			Calendar diaUltimoDoMes = new GregorianCalendar();
			diaPrimeiroDoMes.set(Calendar.DAY_OF_MONTH, 1);
			diaUltimoDoMes.set(Calendar.DAY_OF_MONTH, 30);
			
			Calendar dataSaldo = new GregorianCalendar();
			dataSaldo.add(Calendar.MONTH, -1);
			dataSaldo.add(Calendar.DAY_OF_MONTH, -1);

			LancamentoRN lancamentoRN = new LancamentoRN();
			this.listaMes = lancamentoRN.listar(conta, diaPrimeiroDoMes.getTime(), diaUltimoDoMes.getTime());
			this.saldoGeral = lancamentoRN.saldo(conta, dataSaldo.getTime());
			
			Categoria categoria = null;
			double saldo = this.saldoGeral;
			for (Lancamento lancamento : this.listaMes) {
				categoria = lancamento.getCategoria();
				saldo = saldo + (lancamento.getValor().floatValue() * categoria.getFator());
				this.saldos.add(saldo);
			}
		}
		return this.listaMes;
   }
   
   public List<Lancamento> getListaMesAnterior() {
	   	if (this.listaMesAnterior == null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			Conta conta = contextoBean.getContaAtiva();

			Calendar diaPrimeiroDoMes = new GregorianCalendar();
			Calendar diaUltimoDoMes = new GregorianCalendar();
			diaPrimeiroDoMes.add(Calendar.MONTH, -1);
			diaUltimoDoMes.add(Calendar.MONTH, -1);
			diaPrimeiroDoMes.set(Calendar.DAY_OF_MONTH, 1);
			diaUltimoDoMes.set(Calendar.DAY_OF_MONTH, 30);

			LancamentoRN lancamentoRN = new LancamentoRN();
			this.listaMesAnterior = lancamentoRN.listar(conta, diaPrimeiroDoMes.getTime(), diaUltimoDoMes.getTime());
		}
		return this.listaMesAnterior;
  }

	public void mudouCheque(ValueChangeEvent event) {
		Integer chequeAnterior = (Integer) event.getOldValue();
		if (chequeAnterior != null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			ChequeRN chequeRN = new ChequeRN();
			try {
				chequeRN.desvinculaLancamento(contextoBean.getContaAtiva(), chequeAnterior);
			} catch (RNException e) {
				FacesContext context = FacesContext.getCurrentInstance();
				FacesMessage msg = new FacesMessage(e.getMessage());
				context.addMessage(null, msg);
				return;
			}
		}
	}
	
	public StreamedContent getArquivoRetorno() {
		FacesContext context = FacesContext.getCurrentInstance();
		ContextoBean contextoBean = ContextoUtil.getContextoBean();
		String usuario = contextoBean.getUsuarioLogado().getLogin();
		String nomeRelatorioJasper = "extrato";
		String nomeRelatorioSaida = usuario + "_extrato";
		LancamentoRN lancamentoRN = new LancamentoRN();
		GregorianCalendar calendario = new GregorianCalendar();
		calendario.setTime(this.getDataInicialRelatorio());
		calendario.add(Calendar.DAY_OF_MONTH, -1);
		Date dataSaldo = new Date(calendario.getTimeInMillis());
		RelatorioUtil relatorioUtil = new RelatorioUtil();

		HashMap<String, Object> parametrosRelatorio = new HashMap<String, Object>();
		parametrosRelatorio.put("codigoUsuario", contextoBean.getUsuarioLogado().getCodigo());
		parametrosRelatorio.put("numeroConta", contextoBean.getContaAtiva().getConta());
		parametrosRelatorio.put("dataInicial", this.getDataInicialRelatorio());
		parametrosRelatorio.put("dataFinal", this.getDataFinalRelatorio());
		parametrosRelatorio.put("saldoAnterior", lancamentoRN.saldo(contextoBean.getContaAtiva(), dataSaldo));

		try {
			this.arquivoRetorno = relatorioUtil.geraRelatorio(parametrosRelatorio, nomeRelatorioJasper, nomeRelatorioSaida, RelatorioUtil.RELATORIO_PDF);
		} catch (UtilException e) {
			context.addMessage(null, new FacesMessage("N�o foi poss�vel gerar o relat�rio. Erro: " + e.getMessage()));
			return null;
		} 
		return this.arquivoRetorno;
	}

	public float getSaldoGeral() {
		return saldoGeral;
	}

	public void setSaldoGeral(float saldo) {
		this.saldoGeral = saldo;
	}

	public void setLista(List<Lancamento> lista) {
		this.lista = lista;
	}

	public List<Double> getSaldos() {
		return saldos;
	}

	public void setSaldos(List<Double> saldos) {
		this.saldos = saldos;
	}

	public Lancamento getEditado() {
		return editado;
	}

	public void setEditado(Lancamento selecionado) {
		this.editado = selecionado;
	}

	public Integer getNumeroCheque() {
		return numeroCheque;
	}

	public void setNumeroCheque(Integer numeroCheque) {
		this.numeroCheque = numeroCheque;
	}

	public java.util.Date getDataInicialRelatorio() {
		return dataInicialRelatorio;
	}

	public void setDataInicialRelatorio(java.util.Date dataInicialRelatorio) {
		this.dataInicialRelatorio = dataInicialRelatorio;
	}

	public java.util.Date getDataFinalRelatorio() {
		return dataFinalRelatorio;
	}

	public void setDataFinalRelatorio(java.util.Date dataFinalRelatorio) {
		this.dataFinalRelatorio = dataFinalRelatorio;
	}

	public void setArquivoRetorno(StreamedContent arquivoRetorno) {
		this.arquivoRetorno = arquivoRetorno;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public Integer getPeriodo() {
		return periodo;
	}

}