package br.com.artsoft.finart.model.investimento.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.domain.investimento.Investimento;
import br.com.artsoft.finart.model.repository.InvestimentoRepository;

@SpringBootTest
class InvestimentoRepositoryIntegrationTest {
	
	@Autowired
	InvestimentoRepository investimentoRepository;	

	@Test
	void testFindAllByUsuario() {
		
		Usuario usuario = Usuario.builder().codigo(1).build();		
		
		List<Investimento> investimentos = investimentoRepository.findByUsuario(usuario);
		
		assertNotNull(investimentos);
		assertFalse(investimentos.isEmpty());
	}

}
