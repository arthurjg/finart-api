package br.com.artsoft.finart.controller.dto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.artsoft.finart.model.domain.Investimento;

@Component
public class InvestimentoMapper {
	
	private ModelMapper objectMapper;

	public InvestimentoMapper() {
		super();
		
		objectMapper = new ModelMapper();
	}
	
	public Investimento map(InvestimentoDTO investimento) {
		return objectMapper.map(investimento, Investimento.class);
	}
	
	public InvestimentoDTO converte(Investimento investimento) {
		return objectMapper.map(investimento, InvestimentoDTO.class);
	}	

}
