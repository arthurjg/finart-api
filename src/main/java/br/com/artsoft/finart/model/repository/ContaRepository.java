package br.com.artsoft.finart.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.artsoft.finart.model.domain.Conta;
import br.com.artsoft.finart.model.domain.Usuario;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer> {		

	public List<Conta> findAllByUsuario(Usuario usuario);

	public Conta findByUsuarioAndFavorita(Usuario usuario, boolean favorita);
	
}
