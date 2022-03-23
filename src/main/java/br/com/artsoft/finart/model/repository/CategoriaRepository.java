package br.com.artsoft.finart.model.repository;

import java.util.List;

import br.com.artsoft.finart.model.domain.Categoria;
import br.com.artsoft.finart.model.domain.Usuario;

public interface CategoriaRepository {
	
	public Categoria salvar(Categoria categoria);

	public void excluir(Categoria categoria);

	public Categoria carregar(Integer categoria);

	public List<Categoria> listar(Usuario usuario);

}
