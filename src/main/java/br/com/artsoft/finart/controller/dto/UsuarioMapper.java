package br.com.artsoft.finart.controller.dto;

import br.com.artsoft.finart.model.domain.Usuario;

public class UsuarioMapper {
	
	public static Usuario map(UsuarioDTO usuario) {
		
		String login = usuario.getLogin();
		if(login.contains("@")) {
			login = login.substring(0, login.indexOf("@"));
		}
		if(login.length() > 5) {
			login = login.substring(0, 5);
		}
		
		return Usuario.builder()
			.nome(usuario.getNome())
			.login(login)
			.email(usuario.getEmail())
			.idioma(usuario.getIdioma())
			.nascimento(usuario.getNascimento())
			.senha(usuario.getSenha())
			.celular(usuario.getCelular())
			.ativo(true)			
			.build();
		
	}

}
