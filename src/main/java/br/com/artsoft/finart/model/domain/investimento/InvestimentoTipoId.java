package br.com.artsoft.finart.model.domain.investimento;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum InvestimentoTipoId {
	
	TESOURO_DIRETO("TD"),
	CDB("CDB"),
	LCI("LCI"),
	LCA("LCA"),
	FUNDOS_DE_INVESTIMENTO("FI"),
	ACOES("AC"),
	CRIPTOMOEDA("CRPT");	
	
	String codigo;	

}
