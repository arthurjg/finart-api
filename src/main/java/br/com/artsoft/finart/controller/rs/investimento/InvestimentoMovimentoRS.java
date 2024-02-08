package br.com.artsoft.finart.controller.rs.investimento;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.artsoft.finart.controller.investimento.dto.InvestimentoMovimentoDetalhesDTO;
import br.com.artsoft.finart.controller.investimento.mapper.InvestimentoMovimentoMapper;
import br.com.artsoft.finart.controller.util.ContextoUtil;
import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.domain.investimento.Investimento;
import br.com.artsoft.finart.model.service.ContextoRN;
import br.com.artsoft.finart.model.service.investimento.InvestimentoMovimentoService;
import br.com.artsoft.finart.model.service.investimento.InvestimentoService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/investimentos")
public class InvestimentoMovimentoRS {
	
	ContextoRN contextoRN;
	
	InvestimentoMovimentoService investimentoMovimentoService;
	
	InvestimentoService investimentoService;
	
	InvestimentoMovimentoMapper movimentoMapper;
	
	@GetMapping("/{id}/movimentos")
	public ResponseEntity<List<InvestimentoMovimentoDetalhesDTO>> listar(
			@PathVariable("id") Integer codigo) throws Exception {
		
		Usuario usuarioLogado = contextoRN.getUsuarioLogado(ContextoUtil.getEmailUsuarioLogado());
		Investimento investimento = null;
		try {        	
			investimento = investimentoService.carregar(codigo);
		} catch(IllegalArgumentException exc){
			return ResponseEntity.notFound().build();		
		}		
		
		if( !usuarioLogado.equals( investimento.getUsuario() ) ){
			return ResponseEntity.notFound().build();
		}
		
		List<InvestimentoMovimentoDetalhesDTO> lista = 
				investimentoMovimentoService.listar(investimento)
					.stream()
					.map(inv -> movimentoMapper.converte(inv))
					.collect(Collectors.toList());			
		
		return ResponseEntity.ok(lista);
	}

}
