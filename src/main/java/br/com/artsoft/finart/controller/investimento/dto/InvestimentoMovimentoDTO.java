package br.com.artsoft.finart.controller.investimento.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvestimentoMovimentoDTO {
	
	private Integer id;
	
	@NotBlank
	private String tipo;
	
	@NotNull
	@Positive
	private BigDecimal quantidade;
	
	@NotNull
	@Positive
	private BigDecimal valor;	
	
	@NotBlank
	private String data;

}
