
package br.com.artsoft.finart.model.service.investimento;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.domain.investimento.Investimento;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoMovimento;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoSumario;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoTipo;
import br.com.artsoft.finart.model.domain.investimento.MovimentoTipo;
import br.com.artsoft.finart.model.repository.InvestimentoRepository;
import br.com.artsoft.finart.model.repository.InvestimentoTipoRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InvestimentoService {
	
	private final InvestimentoMovimentoService investimentoMovimentoService;
	
	private final InvestimentoRepository investimentoRepository;
	private final InvestimentoTipoRepository investimentoTipoRepository;	

	public void salvar(Investimento investimento, String tipo) {
		
		Optional<InvestimentoTipo> tipoOptional = investimentoTipoRepository.findByTipo(tipo);
		
		InvestimentoTipo investimentoTipo = tipoOptional.orElseThrow();
		investimento.setTipo(investimentoTipo);
		
		investimentoRepository.save(investimento);
	}

	public void excluir(Investimento investimento) {
		investimentoRepository.delete(investimento);
	}

	public Investimento carregar(Integer codigo) {
		//TODO ALTERAR PARA EXCECAO CUSTOMIZADA
		return investimentoRepository.findById(codigo).orElseThrow(IllegalArgumentException::new);
	}

	public List<InvestimentoSumario> listar(Usuario usuario) {
		
		List<Investimento> investimentos = investimentoRepository.findByUsuario(usuario);	
		
		List<InvestimentoSumario> investimentoSumarios = investimentos.stream()
			.map(investimento -> {
				List<InvestimentoMovimento> movimentos = investimentoMovimentoService.listarBy(investimento);				
			
				BigDecimal somaValorTotalCompra = movimentos
					.stream()
					.filter(movimento -> MovimentoTipo.COMPRA.getCodigo().equals(movimento.getTipo()))
					.map(InvestimentoMovimento::getValorTotal)
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			
				BigDecimal somaPrecoMedioVenda = movimentos
					.stream()
					.filter(movimento -> MovimentoTipo.VENDA.getCodigo().equals(movimento.getTipo()))
					.map(movimento -> {
				
						BigDecimal somaValorTotalCompraAteAData = movimentos
								.stream()
								.filter(movimentoCompra -> MovimentoTipo.COMPRA.getCodigo().equals(movimento.getTipo())
										&& movimentoCompra.getData().isBefore( movimento.getData() ) )
								.map(InvestimentoMovimento::getValorTotal)
								.reduce(BigDecimal.ZERO, BigDecimal::add);
						
						BigDecimal somaQuantidadeCompraAteAData = movimentos
								.stream()
								.filter(movimentoCompra -> MovimentoTipo.COMPRA.getCodigo().equals(movimento.getTipo())
										&& movimentoCompra.getData().isBefore( movimento.getData() ) )
								.map(InvestimentoMovimento::getQuantidade)
								.reduce(BigDecimal.ZERO, BigDecimal::add);
						
						BigDecimal precoMedioCompraAteAData = somaValorTotalCompraAteAData.divide(somaQuantidadeCompraAteAData);
						
						return precoMedioCompraAteAData.multiply(movimento.getQuantidade());							
					})
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			
				BigDecimal somaValorTotal = somaValorTotalCompra.subtract(somaPrecoMedioVenda);	
			
				return new InvestimentoSumario(investimento, somaValorTotal);				 	
			})
			.collect(Collectors.toList());	
		
		
		return investimentoSumarios;
	}	
}
