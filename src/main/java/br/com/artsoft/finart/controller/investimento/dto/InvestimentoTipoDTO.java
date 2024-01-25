package br.com.artsoft.finart.controller.investimento.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvestimentoTipoDTO {
	
	
	private String nome;	
	
	private String tipo;	

}
