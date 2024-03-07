package br.com.artsoft.finart.controller.investimento.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.artsoft.finart.controller.investimento.dto.InvestimentoMovimentoDetalhesDTO;
import br.com.artsoft.finart.model.domain.investimento.Investimento;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoMovimento;

@SpringBootTest
class InvestimentoMovimentoMapperTest {
	
	@Autowired
	InvestimentoMovimentoMapper movimentoMapper;	

	@Test
	void testMap() {
		InvestimentoMovimentoDetalhesDTO movimentoDto = InvestimentoMovimentoDetalhesDTO.builder()
				.data("2024-02-22T11:26:23.000Z")				
				.tipo("C")
				.valor(new BigDecimal("150.00"))
				.build();
		
		Investimento investimento = Investimento.builder()
				.id(155)
				.nome("Tesouro Selic")
				.build();
		
		
		InvestimentoMovimento movimento = movimentoMapper.map(movimentoDto, investimento);
		
		LocalDateTime expectedDate = LocalDateTime.of(2024, 2, 22, 11, 26, 23);
		BigDecimal expectedValor = new BigDecimal("150.00");
		
		assertNotNull(movimento);		
		assertEquals(movimentoDto.getTipo(), movimento.getTipo());
		assertEquals(expectedValor, movimento.getValor());
		assertTrue(expectedDate.isEqual(movimento.getData()));
	}

}
