package br.com.artsoft.finart.controller.investimento.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.artsoft.finart.controller.investimento.dto.InvestimentoMovimentoDetalhesDTO;
import br.com.artsoft.finart.model.domain.investimento.Investimento;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoMovimento;
import br.com.artsoft.finart.model.domain.investimento.MovimentoTipo;

@Component
public class InvestimentoMovimentoMapper {
	
	@Autowired
	private ModelMapper modelMapper;	
	
	public InvestimentoMovimentoDetalhesDTO converte(InvestimentoMovimento movimento) {		
		
		InvestimentoMovimentoDetalhesDTO movimentoDTO = 
				modelMapper.map(movimento, InvestimentoMovimentoDetalhesDTO.class);		
		
		movimentoDTO.setTipo(MovimentoTipo.getByCodigo(movimento.getTipo()).getDescricao());
		
		return movimentoDTO;
	}
	
	public InvestimentoMovimento map(InvestimentoMovimentoDetalhesDTO movimento, Investimento investimento) {
		
		LocalDateTime data = LocalDateTime.parse(movimento.getData(), 
				//DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSZ")
				DateTimeFormatter.ISO_DATE_TIME
				);
		
		return InvestimentoMovimento.builder()
			.data(data)
			.tipo(movimento.getTipo())
			.valor(movimento.getValor())
			.investimento(investimento)
			.build();
		
		//return modelMapper.map(movimento, InvestimentoMovimento.class);
	}

}
