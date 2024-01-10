
package br.com.artsoft.finart.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.domain.investimento.Investimento;

@Repository
public interface InvestimentoRepository extends JpaRepository<Investimento, Integer> {		

	public List<Investimento> findByUsuario(Usuario usuario);

}
