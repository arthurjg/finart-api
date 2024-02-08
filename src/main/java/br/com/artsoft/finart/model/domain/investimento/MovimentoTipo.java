package br.com.artsoft.finart.model.domain.investimento;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MovimentoTipo {
	
	COMPRA("C", "Compra"),
	VENDA("V", "Venda");	
	
	String codigo;
	String descricao;
	
	public static MovimentoTipo getByCodigo(String codigo) {
		
		for(MovimentoTipo tipo : MovimentoTipo.values()) {
			if(tipo.getCodigo().equals(codigo)) {
				return tipo;
			}
		}
		return null;
	}

}
