package br.com.artsoft.finart.controller.rs.investimento;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.artsoft.finart.controller.investimento.dto.InvestimentoTipoDTO;
import br.com.artsoft.finart.controller.investimento.mapper.InvestimentoTipoMapper;
import br.com.artsoft.finart.model.service.ContextoRN;
import br.com.artsoft.finart.model.service.investimento.InvestimentoTipoService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/investimentos/tipos")
public class InvestimentoTipoRS {	
	
	ContextoRN contextoRN;	
	
	InvestimentoTipoService investimentoService;	
	
	InvestimentoTipoMapper investimentoMapper;	

	@GetMapping
	public ResponseEntity<List<InvestimentoTipoDTO>> getLista() throws Exception {
		
		List<InvestimentoTipoDTO> lista = investimentoService.listar()
				.stream()
				.map(inv -> investimentoMapper.converte(inv))
				.collect(Collectors.toList());			
		
		return ResponseEntity.ok(lista);
	}

}
