package br.com.artsoft.finart.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.artsoft.finart.model.domain.Permissao;
import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.repository.UsuarioRepository;

@Service
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
	
	public Usuario carregar( Integer codigo ){
		return this.usuarioRepo.getById(codigo);
	}	
	
	public Usuario buscarPorLogin( String login ){
		return this.usuarioRepo.findByLogin(login);
	}	
	
	public Usuario buscarPorEmail( String email ){
		return this.usuarioRepo.findByEmail(email);
	}	
	
	public void salvar( Usuario usuario ){
		Permissao permissao = new Permissao("ROLE_USUARIO");
		//todo
		usuario.getPermissoes().add(permissao);
		this.usuarioRepo.save(usuario);			
		this.categoriaRN.salvaEstruturaPadrao(usuario);
	}	
	
	public void atualizar( Usuario usuario ){
		this.usuarioRepo.save(usuario);		
	}	
	
	public void excluir( Usuario usuario ){		
		this.categoriaRN.excluir(usuario);
		this.usuarioRepo.delete(usuario);
	}	
	
	public List<Usuario> listar(){
		return this.usuarioRepo.findAll();
	}	

}
