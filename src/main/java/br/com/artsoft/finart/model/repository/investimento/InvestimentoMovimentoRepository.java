package br.com.artsoft.finart.model.repository.investimento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.artsoft.finart.model.domain.investimento.Investimento;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoMovimento;

@Repository
public interface InvestimentoMovimentoRepository extends JpaRepository<InvestimentoMovimento, Integer> {
	
	public List<InvestimentoMovimento> findByInvestimento(Investimento investimento);

}
