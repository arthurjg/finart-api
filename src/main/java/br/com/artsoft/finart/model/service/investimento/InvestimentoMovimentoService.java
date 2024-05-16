package br.com.artsoft.finart.model.service.investimento;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.artsoft.finart.model.domain.investimento.Investimento;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoMovimento;
import br.com.artsoft.finart.model.repository.investimento.InvestimentoMovimentoRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InvestimentoMovimentoService {
	
	private final InvestimentoMovimentoRepository movimentoRepository;
	
	public List<InvestimentoMovimento> listarBy(Investimento investimento) {
		return movimentoRepository.findByInvestimento(investimento);
	}

	public void salvar(InvestimentoMovimento movimento) {
		movimentoRepository.save(movimento);		
	}
	
	public void remover(Integer movimentoId) {		
		movimentoRepository.deleteById(movimentoId); 		
	}

}
