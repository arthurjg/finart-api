package br.com.artsoft.finart.controller.dto;

import br.com.artsoft.finart.model.domain.Usuario;

public class UsuarioMapper {
	
	public static Usuario map(UsuarioDTO usuario) {
		return Usuario.builder()
			.nome(usuario.getNome())
			.login(usuario.getLogin())
			.email(usuario.getEmail())
			.idioma(usuario.getIdioma())
			.nascimento(usuario.getNascimento())
			.senha(usuario.getSenha())
			.celular(usuario.getCelular())
			.ativo(true)			
			.build();
		
	}

}
