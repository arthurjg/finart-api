package br.com.artsoft.finart.controller.investimento.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InvestimentoDetalhesDTO {
	
	private Integer id;
	private String nome;	
	private String tipo;
	private String natureza;
	private String valorAquisicao;

}
