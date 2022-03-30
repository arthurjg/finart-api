package br.com.artsoft.finart.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.artsoft.finart.model.domain.Categoria;
import br.com.artsoft.finart.model.domain.Usuario;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {	

	public List<Categoria> findAllByUsuario(Usuario usuario);

}
