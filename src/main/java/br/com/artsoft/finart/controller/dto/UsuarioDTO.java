package br.com.artsoft.finart.controller.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UsuarioDTO {
	
	private String nome;
	private LocalDate nascimento;
	private String celular;
	private String email;
	private String idioma;
	private String login;
	private String senha;
	private String confirmacaoSenha;
	
	private ContaDTO contaDTO;

}
