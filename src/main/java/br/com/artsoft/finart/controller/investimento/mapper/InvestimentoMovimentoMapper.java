package br.com.artsoft.finart.controller.investimento.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.artsoft.finart.controller.investimento.dto.InvestimentoMovimentoDTO;
import br.com.artsoft.finart.controller.investimento.dto.InvestimentoMovimentoDetalhesDTO;
import br.com.artsoft.finart.model.domain.investimento.Investimento;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoMovimento;
import br.com.artsoft.finart.model.domain.investimento.MovimentoTipo;
import br.com.artsoft.finart.model.util.DateTimeUtil;

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
	
	public InvestimentoMovimento map(InvestimentoMovimentoDTO movimento, Investimento investimento) {
		
		LocalDate data = LocalDate.parse(movimento.getData(), DateTimeFormatter.ISO_DATE);
		LocalDateTime dataHora = DateTimeUtil.getFirstHourByLocalDate(data);
		
		return InvestimentoMovimento.builder()
			.data(dataHora)
			.tipo(movimento.getTipo())
			.quantidade(movimento.getQuantidade())
			.valor(movimento.getValor())
			.investimento(investimento)
			.build();		
	}

}
