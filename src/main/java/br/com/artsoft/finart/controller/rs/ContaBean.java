package br.com.artsoft.finart.controller.rs;


import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.artsoft.finart.controller.rs.util.ContextoUtil;
import br.com.artsoft.finart.controller.rs.util.RelatorioUtil;
import br.com.artsoft.finart.model.domain.Conta;
import br.com.artsoft.finart.model.domain.Lancamento;
import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.service.ContaRN;
import br.com.artsoft.finart.model.service.LancamentoRN;
import financeiro.util.UtilException;

@RestController
@RequestMapping("/conta")
public class ContaBean {
	
	@Autowired
	ContaRN contaRN;	

	@PostMapping
	public ResponseEntity<Conta> salvar(@RequestBody Conta conta) {
		
		ContextoBean contextoBean = ContextoUtil.getContextoBean();		
		Usuario usuariologado = contextoBean.getUsuarioLogado();
		conta.setUsuario( usuariologado );
		
		contaRN.salvar(conta);	
		
		return ResponseEntity.ok(conta);	
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Conta> excluir(@PathVariable("id") Integer codigo) { 
		
		Conta conta = contaRN.carregar(codigo);
		
		if(Objects.isNull(conta)) {
			return ResponseEntity.notFound().build();
		}
		
		contaRN.excluir(conta);		
		
		return ResponseEntity.ok(conta);	
	}

	@PutMapping("/{id}/favorita")
	public ResponseEntity<Conta> tornarFavorita(@PathVariable("id") Integer codigo) { 
		
		Conta conta = contaRN.carregar(codigo);
		
		if(Objects.isNull(conta)) {
			return ResponseEntity.notFound().build();
		}
		
		contaRN.tornarFavorita(conta);
		
		return ResponseEntity.ok(conta);
	}
	
	@GetMapping
	public ResponseEntity<List<Conta>> getLista() { 
		
		ContextoBean contextoBean = ContextoUtil.getContextoBean();

		List<Conta> lista = contaRN.listar(contextoBean.getUsuarioLogado());
		
		return ResponseEntity.ok(lista);
	}
	
	/** TODO geracao de rerlatorio desabilitada
	 
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
	*/

}

