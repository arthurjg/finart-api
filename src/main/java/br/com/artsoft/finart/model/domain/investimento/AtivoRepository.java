package br.com.artsoft.finart.model.domain.investimento;

import java.math.BigDecimal;

public interface AtivoRepository {
	
	BigDecimal getCotacaoAtualByAtivo(String ativo);

}
