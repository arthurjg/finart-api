package br.com.artsoft.finart.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.artsoft.finart.infra.usuario.UsuarioDetailsMapper;
import br.com.artsoft.finart.model.domain.Permissao;
import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioRN implements UserDetailsService {	 
	
	private final UsuarioRepository usuarioRepo;		
	
	private final CategoriaRN categoriaRN;		
	
	private final UsuarioDetailsMapper usuarioMapper;
	
	public Usuario carregar( Integer codigo ){
		return this.usuarioRepo.getById(codigo);
	}	
	
	public Usuario buscarPorLogin( String login ){
		return this.usuarioRepo.findByLogin(login);
	}	
	
	public Usuario buscarPorEmail( String email ){
		return this.usuarioRepo.findByEmail(email);
	}
	
	public Optional<Usuario> buscarPorEmailESenha( String email, String senha ){
		return this.usuarioRepo.findByEmailAndSenha(email, senha);
	}
	
	public void salvar( Usuario usuario ){
		Permissao permissao = new Permissao("ROLE_USUARIO");
		
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {	
		
		Usuario usuario = usuarioRepo.findByEmail(username);
		return usuarioMapper.convertUsuario(usuario);		 
	}	

}
