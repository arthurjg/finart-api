package br.com.artsoft.finart.controller.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.artsoft.finart.model.domain.investimento.Investimento;

@Component
public class InvestimentoMapper {
	
	@Autowired
	private ModelMapper objectMapper;	
	
	public Investimento map(InvestimentoDTO investimento) {
		return objectMapper.map(investimento, Investimento.class);
	}
	
	public InvestimentoDTO converte(Investimento investimento) {		
		return objectMapper.map(investimento, InvestimentoDTO.class);
	}	

}
