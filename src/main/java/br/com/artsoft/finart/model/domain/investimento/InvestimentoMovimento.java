package br.com.artsoft.finart.model.domain.investimento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
@Entity
@Table(name = "investimento_movimento")
public class InvestimentoMovimento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;			
	
	private String tipo;
	
	private BigDecimal quantidade;
	
	private BigDecimal valor;	
	
	private BigDecimal valorTotal;	
	
	private LocalDateTime data;	
	
	@ManyToOne
	@JoinColumn(name = "investimento_id")
	private Investimento investimento;
	
	public InvestimentoMovimento(Integer id, String tipo, BigDecimal quantidade, BigDecimal valor,
			BigDecimal valorTotal, LocalDateTime data, Investimento investimento) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.quantidade = quantidade;
		this.valor = valor;
		this.valorTotal = valorTotal;
		this.data = data;
		this.investimento = investimento;
		
		getValorTotal();
	}	

	public BigDecimal getValorTotal() {
		if(Objects.isNull(valorTotal) 
				&& !Objects.isNull(quantidade)
				&& !Objects.isNull(valor)) {
			
			valorTotal = quantidade.multiply(valor);
		}
		return valorTotal;
	}		

}
