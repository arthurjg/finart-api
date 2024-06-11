package br.com.artsoft.finart.infra.investimento;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AtivoRepositoryApiIntegrationTest {	
	
	@Autowired
	AtivoRepositoryApi ativoRepositoryApi;		

	@Test
	void testGetCotacaoAtualByAtivo() {
		
		String ativo = "PETR4";			
		
		var cotacao = ativoRepositoryApi.getCotacaoAtualByAtivo(ativo);		
		
		assertNotNull(cotacao);	
		assertThat(cotacao.compareTo(BigDecimal.ZERO) >= 0);		
	}

}
