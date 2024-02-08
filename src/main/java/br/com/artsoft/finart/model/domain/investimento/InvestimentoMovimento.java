package br.com.artsoft.finart.model.domain.investimento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
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
	
	private BigDecimal valor;
	
	private LocalDateTime data;
	
	@ManyToOne
	@JoinColumn(name = "investimento_id")
	private Investimento investimento;

}
