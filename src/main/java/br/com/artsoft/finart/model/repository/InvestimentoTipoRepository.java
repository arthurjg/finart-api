
package br.com.artsoft.finart.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.artsoft.finart.model.domain.investimento.InvestimentoTipo;

@Repository
public interface InvestimentoTipoRepository extends JpaRepository<InvestimentoTipo, Integer> {		

	public Optional<InvestimentoTipo> findByTipo(String tipo);

}
