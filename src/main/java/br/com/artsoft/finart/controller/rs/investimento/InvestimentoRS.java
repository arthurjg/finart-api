package br.com.artsoft.finart.controller.rs.investimento;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.artsoft.finart.controller.investimento.dto.InvestimentoDetalhesDTO;
import br.com.artsoft.finart.controller.investimento.dto.InvestimentoMapper;
import br.com.artsoft.finart.controller.investimento.dto.InvestimentoSimplesDTO;
import br.com.artsoft.finart.controller.util.ContextoUtil;
import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.domain.investimento.Investimento;
import br.com.artsoft.finart.model.service.ContextoRN;
import br.com.artsoft.finart.model.service.investimento.InvestimentoService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/investimentos")
public class InvestimentoRS {	
	
	ContextoRN contextoRN;	
	
	InvestimentoService investimentoRN;	
	
	InvestimentoMapper investimentoMapper;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void salvar(@RequestBody InvestimentoSimplesDTO investimentoDto) throws Exception {		
		Usuario usuarioLogado = contextoRN.getUsuarioLogado(ContextoUtil.getEmailUsuarioLogado());		
		Investimento investimento = investimentoMapper.map(investimentoDto);
		
		investimento.setUsuario(usuarioLogado);
		investimentoRN.salvar(investimento, investimentoDto.getTipo());		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable("id") Integer codigo) {	
		
		Investimento investimento = investimentoRN.carregar(codigo);
		investimentoRN.excluir(investimento);	
		
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<InvestimentoDetalhesDTO>> getLista() throws Exception {
		Usuario usuarioLogado = contextoRN.getUsuarioLogado(ContextoUtil.getEmailUsuarioLogado());
		List<InvestimentoDetalhesDTO> lista;
				
		lista = investimentoRN.listar(usuarioLogado)
				.stream()
				.map(inv -> investimentoMapper.converte(inv))
				.collect(Collectors.toList());			
		
		return ResponseEntity.ok(lista);
	}

}
