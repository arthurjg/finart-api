package br.com.artsoft.finart.controller.rs;

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

import br.com.artsoft.finart.controller.dto.InvestimentoDTO;
import br.com.artsoft.finart.controller.dto.InvestimentoMapper;
import br.com.artsoft.finart.controller.util.ContextoUtil;
import br.com.artsoft.finart.model.domain.Investimento;
import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.service.ContextoRN;
import br.com.artsoft.finart.model.service.InvestimentoRN;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/investimento")
public class InvestimentoRS {	
	
	ContextoRN contextoRN;	
	
	InvestimentoRN investimentoRN;	
	
	InvestimentoMapper investimentoMapper;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void salvar(@RequestBody InvestimentoDTO investimentoDto) throws Exception {		
		Usuario usuarioLogado = contextoRN.getUsuarioLogado(ContextoUtil.getLoginUsuarioLogado());		
		Investimento investimento = investimentoMapper.map(investimentoDto);
		
		investimento.setUsuario(usuarioLogado);
		investimentoRN.salvar(investimento);			
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable("id") Integer codigo) {	
		
		Investimento investimento = investimentoRN.carregar(codigo);
		investimentoRN.excluir(investimento);	
		
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<InvestimentoDTO>> getLista() throws Exception {
		Usuario usuarioLogado = contextoRN.getUsuarioLogado(ContextoUtil.getLoginUsuarioLogado());
		List<InvestimentoDTO> lista;
				
		lista = investimentoRN.listar(usuarioLogado)
				.stream()
				.map(inv -> investimentoMapper.converte(inv))
				.collect(Collectors.toList());			
		
		return ResponseEntity.ok(lista);
	}

}
