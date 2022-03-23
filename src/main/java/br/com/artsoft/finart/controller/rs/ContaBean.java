package br.com.artsoft.finart.controller.rs;


import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.StreamedContent;
import org.springframework.web.bind.annotation.RestController;

import br.com.artsoft.finart.controller.rs.util.ContextoUtil;
import br.com.artsoft.finart.controller.rs.util.RelatorioUtil;
import br.com.artsoft.finart.model.domain.Conta;
import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.service.ContaRN;
import financeiro.util.UtilException;

@RestController
public class ContaBean {

	private Conta				selecionada	= new Conta();
	private List<Conta>		lista			= null;
	private StreamedContent	arquivoRetorno;
	private int					tipoRelatorio;

	public void salvar() {
		
		ContextoBean contextoBean = ContextoUtil.getContextoBean();		
		Usuario usuariologado = contextoBean.getUsuarioLogado();
		this.selecionada.setUsuario( usuariologado );
		ContaRN contaRN = new ContaRN();
		contaRN.salvar(this.selecionada);
		this.selecionada = new Conta();
		this.lista = null;		
		
	}

	public void excluir() { //1
		ContaRN contaRN = new ContaRN();
		contaRN.excluir(this.selecionada);
		this.selecionada = new Conta();
		this.lista = null;
	}

	public void tornarFavorita() { //3
		ContaRN contaRN = new ContaRN();
		contaRN.tornarFavorita(this.selecionada);
		this.selecionada = new Conta();
	}
	
	public List<Conta> getLista() { //2
		if (this.lista == null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();

			ContaRN contaRN = new ContaRN();
			this.lista = contaRN.listar(contextoBean.getUsuarioLogado());
		}
		return this.lista;
	}
	
	public StreamedContent getArquivoRetorno() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ContextoBean contextoBean = ContextoUtil.getContextoBean();
		String usuario = contextoBean.getUsuarioLogado().getLogin();
		String nomeRelatorioJasper = "contas";
		String nomeRelatorioSaida = usuario + "_contas";
		RelatorioUtil relatorioUtil = new RelatorioUtil();
		HashMap parametrosRelatorio = new HashMap();
		parametrosRelatorio.put("codigoUsuario", contextoBean.getUsuarioLogado().getCodigo());
		
		try {
			this.arquivoRetorno = 
					relatorioUtil.geraRelatorio(parametrosRelatorio, nomeRelatorioJasper, nomeRelatorioSaida, this.tipoRelatorio);
			
		} catch (UtilException e) {			
			context.addMessage(null, new FacesMessage(e.getMessage()));
			return null;
		}		
		return this.arquivoRetorno;
	}

	public void setArquivoRetorno(StreamedContent arquivoRetorno) {
		this.arquivoRetorno = arquivoRetorno;
	}

	public int getTipoRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoRelatorio(int tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}

	public Conta getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(Conta selecionada) {
		this.selecionada = selecionada;
	}

	

}

