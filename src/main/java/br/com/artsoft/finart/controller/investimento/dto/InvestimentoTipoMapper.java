package br.com.artsoft.finart.controller.investimento.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.artsoft.finart.model.domain.investimento.InvestimentoTipo;

@Component
public class InvestimentoTipoMapper {
	
	@Autowired
	private ModelMapper modelMapper;		
	
	
	public InvestimentoTipoDTO converte(InvestimentoTipo investimentoTipo) {		
		return modelMapper.map(investimentoTipo, InvestimentoTipoDTO.class);
	}	

}
