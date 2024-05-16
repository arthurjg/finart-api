package br.com.artsoft.finart.model.service.investimento;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.domain.investimento.Investimento;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoClasse;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoMovimento;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoSumario;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoTipo;
import br.com.artsoft.finart.model.domain.investimento.MovimentoTipo;
import br.com.artsoft.finart.model.repository.InvestimentoRepository;
import br.com.artsoft.finart.model.repository.InvestimentoTipoRepository;

@ExtendWith(MockitoExtension.class)
class InvestimentoServiceTest {
	
	@InjectMocks
	InvestimentoService service;
	
	@Mock
	InvestimentoRepository	investimentoRepository;	
	
	@Mock
	InvestimentoTipoRepository investimentoTipoRepository;	
	
	@Mock
	InvestimentoMovimentoService investimentoMovimentoService;

	@Test
	void testSalvar() {
		
		Usuario usuario = Usuario.builder()
				.nome("John Nobody")
				.build();		
		
		String tipo = "CDB";
		
		InvestimentoTipo investimentoTipo = InvestimentoTipo.builder()
				.nome(tipo)
				.tipo(tipo)
				.build();
		
		Investimento investimento = Investimento.builder()
				.nome("IVVB11")				
				.usuario(usuario)
				.build();
		
		when(investimentoTipoRepository.findByTipo(tipo)).thenReturn(Optional.of(investimentoTipo));
		
		service.salvar(investimento, tipo);
		
		verify(investimentoRepository).save(investimento);
	}
	
	@Test
	void testListarComSucessoSemMovimentos() {
		
		String nomeInvestimento = "IVVB11";
		
		Usuario usuario = Usuario.builder()
				.nome("John Nobody")
				.build();		
		
		String nomeTipo = "Ações";
		String tipo = "AC";
		
		InvestimentoClasse classe = InvestimentoClasse.builder()
				.nome("Renda Variavel")
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
		
		/*InvestimentoMovimento movimento = InvestimentoMovimento.builder()
				.data(LocalDateTime.now())
				.quantidade(BigDecimal.ONE)
				.valor(BigDecimal.TEN)
				.build();*/
				
		List<Investimento> investimentosMock = List.of(investimento);
		
		//List<InvestimentoMovimento> movimentos = List.of(movimento);
		
		when(investimentoRepository.findByUsuario(usuario)).thenReturn(investimentosMock);
		
		//when(investimentoMovimentoService.listarBy(investimento)).thenReturn(movimentos);
		
		List<InvestimentoSumario> investimentos = service.listar(usuario);
		
		assertNotNull(investimentos);
		assertFalse(investimentos.isEmpty());	
		
		InvestimentoSumario investimentoResultado = investimentos.get(0);
		
		assertNotNull(investimentoResultado);
		assertEquals(nomeInvestimento, investimentoResultado.getNome());
		assertEquals(investimentoTipo, investimentoResultado.getTipo());
		assertEquals(usuario, investimentoResultado.getUsuario());
		assertEquals(BigDecimal.ZERO, investimentoResultado.getValorAquisicao());
		
	}
	
	@Test
	void testListarComSucessoComMovimentos() {
		
		String nomeInvestimento = "IVVB11";
		
		Usuario usuario = Usuario.builder()
				.nome("John Nobody")
				.build();		
		
		String nomeTipo = "Ações";
		String tipo = "AC";
		
		InvestimentoClasse classe = InvestimentoClasse.builder()
				.nome("Renda Variavel")
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
		
		InvestimentoMovimento movimento = InvestimentoMovimento.builder()
				.data(LocalDateTime.now())
				.tipo(MovimentoTipo.COMPRA.getCodigo())
				.quantidade(BigDecimal.ONE)
				.valor(BigDecimal.TEN)
				.build();
				
		List<Investimento> investimentosMock = List.of(investimento);		
		List<InvestimentoMovimento> movimentos = List.of(movimento);
		
		when(investimentoRepository.findByUsuario(usuario)).thenReturn(investimentosMock);		
		when(investimentoMovimentoService.listarBy(investimento)).thenReturn(movimentos);
		
		List<InvestimentoSumario> investimentos = service.listar(usuario);
		
		assertNotNull(investimentos);
		assertFalse(investimentos.isEmpty());	
		
		InvestimentoSumario investimentoResultado = investimentos.get(0);
		
		assertNotNull(investimentoResultado);
		assertEquals(nomeInvestimento, investimentoResultado.getNome());
		assertEquals(investimentoTipo, investimentoResultado.getTipo());
		assertEquals(usuario, investimentoResultado.getUsuario());
		assertEquals(BigDecimal.TEN, investimentoResultado.getValorAquisicao());
		
	}

}
