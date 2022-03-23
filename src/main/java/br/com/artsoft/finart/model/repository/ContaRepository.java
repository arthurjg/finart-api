package br.com.artsoft.finart.model.repository;

import java.util.List;

import br.com.artsoft.finart.model.domain.Conta;
import br.com.artsoft.finart.model.domain.Usuario;

public interface ContaRepository {

	public void salvar(Conta conta);

	public void excluir(Conta conta);

	public Conta carregar(Integer conta);

	public List<Conta> listar(Usuario usuario);

	public Conta buscarFavorita(Usuario usuario);
	
}
