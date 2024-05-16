package br.com.artsoft.finart.controller.rs.investimento;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.artsoft.finart.controller.investimento.dto.InvestimentoMovimentoDTO;
import br.com.artsoft.finart.controller.investimento.dto.InvestimentoMovimentoDetalhesDTO;
import br.com.artsoft.finart.controller.investimento.mapper.InvestimentoMovimentoMapper;
import br.com.artsoft.finart.controller.util.ContextoUtil;
import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.domain.investimento.Investimento;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoMovimento;
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
	
	@GetMapping("/{investimentoId}/movimentos")
	public ResponseEntity<List<InvestimentoMovimentoDetalhesDTO>> listar(
			@PathVariable("investimentoId") Integer codigo) throws Exception {		
		
		Optional<Investimento> investimentoExiste = getInvestimento(codigo);
		if(investimentoExiste.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Investimento investimento = investimentoExiste.get();
		
		List<InvestimentoMovimentoDetalhesDTO> lista = 
				investimentoMovimentoService.listarBy(investimento)
					.stream()
					.map(inv -> movimentoMapper.converte(inv))
					.collect(Collectors.toList());			
		
		return ResponseEntity.ok(lista);
	}	
	
	@PostMapping("/{investimentoId}/movimentos")
	public ResponseEntity<Void> salvar(
			@PathVariable("investimentoId") Integer codigo,
			@RequestBody @Validated InvestimentoMovimentoDTO movimentoDto, 
			UriComponentsBuilder uriBuilder) throws Exception {				
		
		Optional<Investimento> investimentoExiste = getInvestimento(codigo);
		if(investimentoExiste.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Investimento investimento = investimentoExiste.get();
		
		InvestimentoMovimento movimento = movimentoMapper.map(movimentoDto, investimento);		
		
		investimentoMovimentoService.salvar(movimento);
		
		URI endereco = uriBuilder.path("/investimentos/{id}/movimentos").buildAndExpand(movimento.getId()).toUri();
		
		return ResponseEntity.created(endereco).build();
	}	
	
	@DeleteMapping("/{investimentoId}/movimentos/{movimentoId}")
	public ResponseEntity<Void> excluir(
			@PathVariable("investimentoId") Integer codigo, 
			@PathVariable("movimentoId") Integer movimentoId) {	
		
		Optional<Investimento> investimentoExiste = getInvestimento(codigo);
		if(investimentoExiste.isEmpty()) {
			return ResponseEntity.notFound().build();
		}				
		
		investimentoMovimentoService.remover(movimentoId);	
		
		return ResponseEntity.noContent().build();
	}
	
	private Optional<Investimento> getInvestimento(Integer codigo){
		Usuario usuarioLogado = contextoRN.getUsuarioLogado(ContextoUtil.getEmailUsuarioLogado());	
		Investimento investimento = null;
		try {        	
			investimento = investimentoService.carregar(codigo);
		} catch(IllegalArgumentException exc){
			return Optional.empty();		
		}		
		
		if( !usuarioLogado.equals( investimento.getUsuario() ) ){
			return Optional.empty();
		}
		return Optional.of(investimento);
	}

}
