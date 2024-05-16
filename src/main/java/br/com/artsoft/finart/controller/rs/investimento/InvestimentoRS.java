package br.com.artsoft.finart.controller.rs.investimento;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.artsoft.finart.controller.investimento.dto.InvestimentoDetalhesDTO;
import br.com.artsoft.finart.controller.investimento.dto.InvestimentoSimplesDTO;
import br.com.artsoft.finart.controller.investimento.mapper.InvestimentoMapper;
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
	
	InvestimentoService investimentoService;	
	
	InvestimentoMapper investimentoMapper;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void salvar(@RequestBody @Validated InvestimentoSimplesDTO investimentoDto) throws Exception {		
		Usuario usuarioLogado = contextoRN.getUsuarioLogado(ContextoUtil.getEmailUsuarioLogado());		
		Investimento investimento = investimentoMapper.map(investimentoDto);
		
		investimento.setUsuario(usuarioLogado);
		investimentoService.salvar(investimento, investimentoDto.getTipo());		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizar(@PathVariable("id") Integer codigo,
			@RequestBody @Validated InvestimentoSimplesDTO investimentoDto) {	
		
		Investimento investimento = investimentoMapper.map(investimentoDto);
		Usuario usuarioLogado = contextoRN.getUsuarioLogado(ContextoUtil.getEmailUsuarioLogado());
		
		investimento.setId(codigo);
		investimento.setUsuario(usuarioLogado);
		investimentoService.salvar(investimento, investimentoDto.getTipo());	
		
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable("id") Integer codigo) {	
		
		Investimento investimento = investimentoService.carregar(codigo);
		investimentoService.excluir(investimento);	
		
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<InvestimentoDetalhesDTO>> listar() throws Exception {
		Usuario usuarioLogado = contextoRN.getUsuarioLogado(ContextoUtil.getEmailUsuarioLogado());
		List<InvestimentoDetalhesDTO> lista = 
				investimentoService.listar(usuarioLogado)
					.stream()
					.map(inv -> investimentoMapper.converte(inv))
					.collect(Collectors.toList());			
		
		return ResponseEntity.ok(lista);
	}

}
