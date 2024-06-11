package br.com.artsoft.finart.controller.investimento.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvestimentoSimplesDTO {
	
	@NotBlank
	private String nome;	
	
	private String codigo;
	
	@NotBlank
	private String tipo;	

}
