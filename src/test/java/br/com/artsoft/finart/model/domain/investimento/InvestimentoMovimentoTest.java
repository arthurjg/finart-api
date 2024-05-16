package br.com.artsoft.finart.model.domain.investimento;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InvestimentoMovimentoTest {
	
	@InjectMocks
	InvestimentoMovimento movimento;

	@BeforeEach
	void setUp() throws Exception {
		
		movimento = InvestimentoMovimento.builder()
				.quantidade(BigDecimal.ONE)
				.valor(BigDecimal.TEN)
				.build();
	}

	@Test
	void testGetValorTotal_Sucesso() {
		
		BigDecimal expected = BigDecimal.valueOf(10);
		
		assertEquals(expected, movimento.getValorTotal()); 
	}

}
