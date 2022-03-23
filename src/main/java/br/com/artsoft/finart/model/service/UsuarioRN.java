package br.com.artsoft.finart.model.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.artsoft.finart.model.domain.Permissao;
import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.repository.UsuarioRepository;


public class UsuarioRN {
	 
	@Autowired
	private UsuarioRepository usuarioRepo;	
	
	@Autowired
	private CategoriaRN categoriaRN;

	public UsuarioRN() {			
	}
	
	public UsuarioRN(UsuarioRepository usuarioRepo) {		
		this.usuarioRepo = usuarioRepo;
	}
	
	public UsuarioRN(UsuarioRepository usuarioRepo, CategoriaRN categoriaRN) {		
		this.usuarioRepo = usuarioRepo;
		this.categoriaRN = categoriaRN;
	}	
	
	@Transactional
	public Usuario carregar( Integer codigo ){
		return this.usuarioRepo.carregar(codigo);
	}
	
	@Transactional
	public Usuario buscarPorLogin( String login ){
		return this.usuarioRepo.buscarPorLogin(login);
	}
	
	@Transactional
	public Usuario buscarPorEmail( String email ){
		return this.usuarioRepo.buscarPorEmail(email);
	}
	
	@Transactional
	public void salvar( Usuario usuario ){
		Permissao permissao = new Permissao("ROLE_USUARIO");
		//todo
		usuario.getPermissoes().add(permissao);
		this.usuarioRepo.salvar(usuario);			
		this.categoriaRN.salvaEstruturaPadrao(usuario);
	}
	
	@Transactional
	public void atualizar( Usuario usuario ){
		this.usuarioRepo.atualizar(usuario);		
	}
	
	@Transactional
	public void excluir( Usuario usuario ){		
		this.categoriaRN.excluir(usuario);
		this.usuarioRepo.excluir(usuario);
	}
	
	@Transactional
	public List<Usuario> listar(){
		return this.usuarioRepo.listar();
	}	

}
