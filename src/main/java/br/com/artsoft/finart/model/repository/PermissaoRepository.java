package br.com.artsoft.finart.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.artsoft.finart.model.domain.Permissao;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Integer> {	
	
	public Permissao findByNome( String nome );	

}
