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
import br.com.artsoft.finart.model.domain.investimento.AtivoRepository;
import br.com.artsoft.finart.model.domain.investimento.Investimento;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoClasse;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoMovimento;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoSumario;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoTipo;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoTipoId;
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
	
	@Mock
	AtivoRepository ativoRepository;

	@Test
	void testSalvar() {
		
		Usuario usuario = Usuario.builder()
				.nome("John Nobody")
				.build();		
		
		String tipo = InvestimentoTipoId.CDB.getCodigo();
		
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
		
		String nomeInvestimento = "ISHARES SP500";
		String codigo = "IVVB11";
		
		Usuario usuario = Usuario.builder()
				.nome("John Nobody")
				.build();		
		
		String nomeTipo = InvestimentoTipoId.ACOES.getCodigo();		
		
		InvestimentoClasse classe = InvestimentoClasse.builder()
				.nome("Renda Variavel")
				.classe("RV")
				.build();
		
		InvestimentoTipo investimentoTipo = InvestimentoTipo.builder()
				.nome(nomeTipo)
				.tipo(nomeTipo)
				.classe(classe)
				.build();
		
		Investimento investimento = Investimento.builder()
				.nome(nomeInvestimento)		
				.codigo(codigo)		
				.tipo(investimentoTipo)
				.usuario(usuario)
				.build();		
				
		List<Investimento> investimentosMock = List.of(investimento);
		
		List<InvestimentoMovimento> movimentos = List.of();
		
		when(investimentoRepository.findByUsuario(usuario)).thenReturn(investimentosMock);		
		when(investimentoMovimentoService.listarBy(investimento)).thenReturn(movimentos);
		when(ativoRepository.getCotacaoAtualByAtivo(investimento.getCodigo())).thenReturn(BigDecimal.TEN);
		
		List<InvestimentoSumario> investimentos = service.listar(usuario);
		
		assertNotNull(investimentos);
		assertFalse(investimentos.isEmpty());	
		
		InvestimentoSumario investimentoResultado = investimentos.get(0);
		
		assertNotNull(investimentoResultado);
		assertEquals(nomeInvestimento, investimentoResultado.getNome());
		assertEquals(codigo, investimentoResultado.getCodigo());
		assertEquals(investimentoTipo, investimentoResultado.getTipo());
		assertEquals(usuario, investimentoResultado.getUsuario());
		assertEquals(BigDecimal.ZERO, investimentoResultado.getValorAquisicao());
		
	}
	
	@Test
	void testListarComSucessoComMovimentosInvestimento() {
		
		String nomeInvestimento = "Tesouro Selic";
		String codigo = "INTB";
		
		Usuario usuario = Usuario.builder()
				.nome("John Nobody")
				.build();		
		
		String nomeTipo = InvestimentoTipoId.ACOES.getCodigo();
		
		InvestimentoClasse classe = InvestimentoClasse.builder()
				.nome("Renda Variavel")
				.classe("RV")
				.build();
		
		InvestimentoTipo investimentoTipo = InvestimentoTipo.builder()
				.nome(nomeTipo)
				.tipo(nomeTipo)
				.classe(classe)
				.build();
		
		Investimento investimento = Investimento.builder()
				.nome(nomeInvestimento)		
				.tipo(investimentoTipo)
				.codigo(codigo)
				.usuario(usuario)
				.build();
		
		InvestimentoMovimento movimento1 = InvestimentoMovimento.builder()
				.data(LocalDateTime.of(2023, 11, 15, 0, 0))
				.tipo(MovimentoTipo.COMPRA.getCodigo())
				.quantidade(new BigDecimal("16.00"))
				.valor(new BigDecimal("187.28"))
				.build();
		
		InvestimentoMovimento movimento2 = InvestimentoMovimento.builder()
				.data(LocalDateTime.of(2024, 3, 14, 0, 0))				
				.tipo(MovimentoTipo.VENDA.getCodigo())
				.quantidade(new BigDecimal("5.00"))
				.valor(new BigDecimal("285.70"))
				.build();
				
		List<Investimento> investimentosMock = List.of(investimento);		
		List<InvestimentoMovimento> movimentos = List.of(movimento1, movimento2);
		
		when(investimentoRepository.findByUsuario(usuario)).thenReturn(investimentosMock);		
		when(investimentoMovimentoService.listarBy(investimento)).thenReturn(movimentos);
		when(ativoRepository.getCotacaoAtualByAtivo(investimento.getCodigo())).thenReturn(new BigDecimal("308.68"));
		
		BigDecimal valorAquisicaoExpected = new BigDecimal("2059.71");
		BigDecimal valorPatrimonialExpected = new BigDecimal("3395.48");
		BigDecimal diferencaoValorTotalExpected = new BigDecimal("1335.77");
		BigDecimal percentualValorTotalExpected = new BigDecimal("64.85");
		
		List<InvestimentoSumario> investimentos = service.listar(usuario);
		
		assertNotNull(investimentos);
		assertFalse(investimentos.isEmpty());	
		
		InvestimentoSumario investimentoResultado = investimentos.get(0);
		
		assertNotNull(investimentoResultado);
		assertEquals(nomeInvestimento, investimentoResultado.getNome());
		assertEquals(codigo, investimentoResultado.getCodigo());
		assertEquals(investimentoTipo, investimentoResultado.getTipo());
		assertEquals(usuario, investimentoResultado.getUsuario());
		assertEquals(valorAquisicaoExpected.doubleValue(), investimentoResultado.getValorAquisicao().doubleValue(), 0.5);
		assertEquals(valorPatrimonialExpected.doubleValue(), investimentoResultado.getValorDeMercado().doubleValue(), 0.5);
		assertEquals(diferencaoValorTotalExpected.doubleValue(), investimentoResultado.getDiferencaValorTotal().doubleValue(), 0.5);
		assertEquals(percentualValorTotalExpected.doubleValue(), investimentoResultado.getPercentualValorTotal().doubleValue(), 0.5);
		
	}
	
	@Test
	void testListarComSucessoComMovimentosInvestimentoNaoAcoes() {
		
		String nomeInvestimento = "ISHARES SP500";
		String codigo = "IVVB11";
		
		Usuario usuario = Usuario.builder()
				.nome("John Nobody")
				.build();		
		
		String nomeTipo = "TESOURO_DIRETO";		
		
		InvestimentoClasse classe = InvestimentoClasse.builder()
				.nome("Renda Variavel")
				.classe("RV")
				.build();
		
		InvestimentoTipo investimentoTipo = InvestimentoTipo.builder()
				.nome(nomeTipo)
				.tipo(InvestimentoTipoId.TESOURO_DIRETO.getCodigo())
				.classe(classe)
				.build();
		
		Investimento investimento = Investimento.builder()
				.nome(nomeInvestimento)		
				.tipo(investimentoTipo)
				.codigo(codigo)
				.usuario(usuario)
				.build();
		
		InvestimentoMovimento movimento1 = InvestimentoMovimento.builder()
				.data(LocalDateTime.of(2023, 11, 15, 0, 0))
				.tipo(MovimentoTipo.COMPRA.getCodigo())
				.quantidade(new BigDecimal("16.00"))
				.valor(new BigDecimal("187.28"))
				.build();
		
		InvestimentoMovimento movimento2 = InvestimentoMovimento.builder()
				.data(LocalDateTime.of(2024, 3, 14, 0, 0))				
				.tipo(MovimentoTipo.VENDA.getCodigo())
				.quantidade(new BigDecimal("5.00"))
				.valor(new BigDecimal("285.70"))
				.build();
				
		List<Investimento> investimentosMock = List.of(investimento);		
		List<InvestimentoMovimento> movimentos = List.of(movimento1, movimento2);
		
		when(investimentoRepository.findByUsuario(usuario)).thenReturn(investimentosMock);		
		when(investimentoMovimentoService.listarBy(investimento)).thenReturn(movimentos);		
		
		BigDecimal valorAquisicaoExpected = new BigDecimal("2059.71");		
		
		List<InvestimentoSumario> investimentos = service.listar(usuario);
		
		assertNotNull(investimentos);
		assertFalse(investimentos.isEmpty());	
		
		InvestimentoSumario investimentoResultado = investimentos.get(0);
		
		assertNotNull(investimentoResultado);
		assertEquals(nomeInvestimento, investimentoResultado.getNome());
		assertEquals(codigo, investimentoResultado.getCodigo());
		assertEquals(investimentoTipo, investimentoResultado.getTipo());
		assertEquals(usuario, investimentoResultado.getUsuario());
		assertEquals(valorAquisicaoExpected.doubleValue(), investimentoResultado.getValorAquisicao().doubleValue(), 0.5);		
	}	

}
