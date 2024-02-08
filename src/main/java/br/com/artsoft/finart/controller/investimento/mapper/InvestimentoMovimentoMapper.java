package br.com.artsoft.finart.controller.investimento.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.artsoft.finart.controller.investimento.dto.InvestimentoMovimentoDetalhesDTO;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoMovimento;
import br.com.artsoft.finart.model.domain.investimento.MovimentoTipo;

@Component
public class InvestimentoMovimentoMapper {
	
	@Autowired
	private ModelMapper objectMapper;	
	
	public InvestimentoMovimentoDetalhesDTO converte(InvestimentoMovimento movimento) {		
		
		InvestimentoMovimentoDetalhesDTO movimentoDTO = 
				objectMapper.map(movimento, InvestimentoMovimentoDetalhesDTO.class);		
		
		movimentoDTO.setTipo(MovimentoTipo.getByCodigo(movimento.getTipo()).getDescricao());
		
		return movimentoDTO;
	}	

}
