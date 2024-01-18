package br.com.artsoft.finart.controller.investimento.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.artsoft.finart.model.domain.investimento.Investimento;

@SpringBootTest
class InvestimentoMapperTest {
	
	@Autowired
	InvestimentoMapper mapper;	

	@Test
	void testMap() {
		
		InvestimentoSimplesDTO investimentoDto = InvestimentoSimplesDTO.builder()
				.nome("CDB BANCO INTER")
				.tipo("CDB")
				.build();
		
		
		Investimento investimento = mapper.map(investimentoDto);
		
		assertNotNull(investimento);
		assertEquals(investimentoDto.getNome(), investimento.getNome());		
	}

}
