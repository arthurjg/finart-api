package br.com.artsoft.finart.model.domain.investimento;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.artsoft.finart.model.domain.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "investimento")
public class Investimento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;	
	
	private String nome;	
	private String codigo;	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tipo_id")
	private InvestimentoTipo tipo;	
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;	

}
