package br.com.artsoft.finart.model.domain.investimento;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class InvestimentoSumario extends Investimento {
	
	private BigDecimal valorAquisicao;

	public InvestimentoSumario(Investimento investimento, BigDecimal valorAquisicao) {
		super();
		setId(investimento.getId());
		setNome(investimento.getNome());
		setTipo(investimento.getTipo());
		setUsuario(investimento.getUsuario());	
		
		this.valorAquisicao = valorAquisicao;
	}	

}
