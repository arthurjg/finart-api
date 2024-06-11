package br.com.artsoft.finart.infra.investimento.ativo;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AtivoSumarioDTO {
	
	private String currency;	
	private String shortName;
	private BigDecimal regularMarketPrice;	
	
	private AtivoInfo info;	

}
