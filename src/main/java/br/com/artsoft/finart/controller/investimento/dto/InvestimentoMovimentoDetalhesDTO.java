package br.com.artsoft.finart.controller.investimento.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvestimentoMovimentoDetalhesDTO extends InvestimentoMovimentoDTO {		
	
	private BigDecimal valorTotal;		

}
