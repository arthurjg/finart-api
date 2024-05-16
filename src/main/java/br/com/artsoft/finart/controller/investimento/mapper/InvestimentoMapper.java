package br.com.artsoft.finart.controller.investimento.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.artsoft.finart.controller.investimento.dto.InvestimentoDetalhesDTO;
import br.com.artsoft.finart.controller.investimento.dto.InvestimentoSimplesDTO;
import br.com.artsoft.finart.model.domain.investimento.Investimento;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoSumario;

@Component
public class InvestimentoMapper {
	
	@Autowired
	private ModelMapper objectMapper;	
	
	public Investimento map(InvestimentoSimplesDTO investimento) {
		return objectMapper.map(investimento, Investimento.class);
	}
	
	public InvestimentoDetalhesDTO converte(InvestimentoSumario investimentoSumario) {		
		return objectMapper.map(investimentoSumario, InvestimentoDetalhesDTO.class);
	}	

}
