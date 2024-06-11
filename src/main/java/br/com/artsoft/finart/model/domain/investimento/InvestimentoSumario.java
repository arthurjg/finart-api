package br.com.artsoft.finart.model.domain.investimento;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class InvestimentoSumario extends Investimento {
	
	private BigDecimal valorAquisicao;
	private BigDecimal precoDeMercado;
	private BigDecimal valorDeMercado;
	private BigDecimal diferencaValorTotal;
	private BigDecimal percentualValorTotal;

	public InvestimentoSumario(Investimento investimento, BigDecimal valorAquisicao, 
			BigDecimal precoDeMercado, BigDecimal valorDeMercado,
			BigDecimal diferencaValorTotal, BigDecimal percentualValorTotal
			) {
		super();
		
		setId(investimento.getId());
		setNome(investimento.getNome());
		setCodigo(investimento.getCodigo());
		setTipo(investimento.getTipo());
		setUsuario(investimento.getUsuario());	
		this.valorAquisicao = valorAquisicao;
		this.precoDeMercado = precoDeMercado;
		this.valorDeMercado = valorDeMercado;
		this.diferencaValorTotal = diferencaValorTotal;
		this.percentualValorTotal = percentualValorTotal;
	}	

}
