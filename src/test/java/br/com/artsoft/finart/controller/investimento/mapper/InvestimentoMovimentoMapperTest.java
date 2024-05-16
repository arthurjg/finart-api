package br.com.artsoft.finart.controller.investimento.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.artsoft.finart.controller.investimento.dto.InvestimentoMovimentoDTO;
import br.com.artsoft.finart.controller.investimento.dto.InvestimentoMovimentoDetalhesDTO;
import br.com.artsoft.finart.model.domain.investimento.Investimento;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoMovimento;

@SpringBootTest
class InvestimentoMovimentoMapperTest {
	
	@Autowired
	InvestimentoMovimentoMapper movimentoMapper;	

	@Test
	void testMap() {
		InvestimentoMovimentoDTO movimentoDto = InvestimentoMovimentoDTO.builder()
				.data("2024-02-22")				
				.tipo("C")
				.quantidade(new BigDecimal("2"))
				.valor(new BigDecimal("150.00"))
				.build();
		
		Investimento investimento = Investimento.builder()
				.id(155)
				.nome("Tesouro Selic")
				.build();
		
		
		InvestimentoMovimento movimento = movimentoMapper.map(movimentoDto, investimento);
		
		LocalDateTime expectedDate = LocalDateTime.of(2024, 2, 22, 0, 0, 0);
		BigDecimal expectedValor = new BigDecimal("300.00");
		
		assertNotNull(movimento);		
		assertEquals(movimentoDto.getTipo(), movimento.getTipo());
		assertEquals(expectedValor, movimento.getValorTotal());
		assertTrue(expectedDate.isEqual(movimento.getData()));
	}
	
	@Test
	void testConverte() {		
		
		InvestimentoMovimento movimento = InvestimentoMovimento.builder()
				.data(LocalDateTime.of(2024, 2, 22, 0, 0, 0))
				.id(100)
				.tipo("C")
				.quantidade(new BigDecimal("2"))
				.valor(new BigDecimal("150.00"))
				.build();		
		
		BigDecimal expectedValor = new BigDecimal("300.00");
		
		InvestimentoMovimentoDetalhesDTO movimentoDTO = movimentoMapper.converte(movimento);
		
		assertNotNull(movimentoDTO);		
		assertEquals(expectedValor, movimentoDTO.getValorTotal());		
	}

}
