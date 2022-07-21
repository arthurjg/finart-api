package br.com.artsoft.finart.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.artsoft.finart.model.domain.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {	
	
	public Usuario findByLogin( String login );
	public Usuario findByEmail( String email );	
	public Optional<Usuario> findByEmailAndSenha( String email, String senha );	

}
