package br.com.artsoft.finart.model.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.artsoft.finart.model.domain.Acao;
import br.com.artsoft.finart.model.service.yahoofinance.YahooProperties;

class YahooFinanceComponentTest {
	
	YahooFinanceComponent yahooFinance;

	@BeforeEach
	void setUp() throws Exception {
		yahooFinance = new YahooFinanceComponent();
	}

	@Test
	void testRetornaCotacao() {
		
		Acao acao = new Acao();
		acao.setSigla("PETR4");
		acao.setOrigem(Character.valueOf('B'));
		int indiceInformacao = YahooProperties.ULTIMO_PRECO_DIA_ACAO_INDICE;
		
		
		try {
			String cotacao = yahooFinance.retornaCotacao(acao, indiceInformacao, acao.getSigla());
			System.out.println("****** cotacao de " + acao.getSigla() + ": " + cotacao);
		} catch (IOException e) {			
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
