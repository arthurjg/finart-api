package br.com.artsoft.finart.infra.investimento;

import lombok.Value;

@Value
public class InvestimentoDetalhado {
	
	private Integer id;
	private String nome;	
	private String tipo;
	private String natureza;
	private String saldoAquisicao;

}
