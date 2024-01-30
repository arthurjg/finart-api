package br.com.artsoft.finart.controller.investimento.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvestimentoDetalhesDTO {
	
	private Integer id;
	private String nome;
	private String tipo;
	private String natureza;

}
