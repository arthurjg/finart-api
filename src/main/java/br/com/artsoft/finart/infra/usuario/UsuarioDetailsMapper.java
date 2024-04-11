package br.com.artsoft.finart.infra.usuario;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import br.com.artsoft.finart.model.domain.Usuario;

@Component
public class UsuarioDetailsMapper {
	
	public UserDetails convertUsuario(Usuario usuario) {
		return new User(usuario.getEmail(), usuario.getSenha(), 
				usuario.getPermissoes()
					.stream()
					.map(permissao -> new SimpleGrantedAuthority(permissao.getNome()))
					.collect(Collectors.toList()));	
	}
	
	public Usuario mapTo(UserDetails userDetails) {
		
		return Usuario.builder()
				.email(userDetails.getUsername())
				.build();	
	}

}
