package br.com.artsoft.finart.infra.investimento;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.artsoft.finart.infra.investimento.ativo.AtivoClient;
import br.com.artsoft.finart.infra.investimento.ativo.AtivoInfoRequisicao;
import br.com.artsoft.finart.infra.investimento.ativo.AtivoSumarioDTO;

@ExtendWith(MockitoExtension.class)
class AtivoRepositoryApiTest {
	
	@InjectMocks
	AtivoRepositoryApi ativoRepositoryApi;
	
	@Mock
	AtivoClient ativoClient;	

	@Test
	void testGetCotacaoAtualByAtivo() {
		
		String ativo = "PETR4";
		
		AtivoSumarioDTO ativoSumarioDTO = AtivoSumarioDTO.builder()
				.shortName("PETROBRAS   PN      N2")
				.regularMarketPrice(BigDecimal.TEN)
				.build();
		
		AtivoInfoRequisicao retornoMock = AtivoInfoRequisicao.builder()
				.results(List.of(ativoSumarioDTO))
				.build();
		
		when(ativoClient.buscarInformacoesAtivo(ativo, null)).thenReturn(retornoMock);
		
		BigDecimal cotacaoExpected = BigDecimal.TEN;
		BigDecimal cotacao = ativoRepositoryApi.getCotacaoAtualByAtivo(ativo);
		
		assertEquals(cotacaoExpected, cotacao);		
	}

}
