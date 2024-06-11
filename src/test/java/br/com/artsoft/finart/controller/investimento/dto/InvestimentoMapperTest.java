package br.com.artsoft.finart.controller.investimento.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.artsoft.finart.controller.investimento.mapper.InvestimentoMapper;
import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.domain.investimento.Investimento;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoClasse;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoSumario;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoTipo;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoTipoId;

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
	
	@Test
	void testConverte() {
		
		String nomeInvestimento = "IVVB11";
		String natureza = "Renda Variavel";
		
		Usuario usuario = Usuario.builder()
				.nome("John Nobody")
				.build();		
		
		String nomeTipo = "Ações";
		String tipo = InvestimentoTipoId.ACOES.getCodigo();
		
		InvestimentoClasse classe = InvestimentoClasse.builder()
				.nome(natureza)
				.classe("RV")
				.build();
		
		InvestimentoTipo investimentoTipo = InvestimentoTipo.builder()
				.nome(nomeTipo)
				.tipo(tipo)
				.classe(classe)
				.build();
		
		Investimento investimento = Investimento.builder()
				.nome(nomeInvestimento)		
				.tipo(investimentoTipo)
				.usuario(usuario)
				.build();
		
		BigDecimal valorAquisicao = BigDecimal.TEN;	
		BigDecimal precoMercado = BigDecimal.TEN;	
		InvestimentoSumario investimentoSumario = new InvestimentoSumario(investimento, valorAquisicao, precoMercado, null, null, null);		
		
		InvestimentoDetalhesDTO investimentoDetalhes = mapper.converte(investimentoSumario);
		
		assertNotNull(investimentoDetalhes);
		assertEquals(investimento.getNome(), investimentoDetalhes.getNome());
		assertEquals(nomeTipo, investimentoDetalhes.getTipo());
		assertEquals(natureza, investimentoDetalhes.getNatureza());
		assertEquals("10", investimentoDetalhes.getValorAquisicao());
	}

}
