package br.com.artsoft.finart.controller.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UsuarioDTO {	
	
	@NotBlank
	private String nome;
	
	private LocalDate nascimento;
	
	private String celular;
	
	@Email
	@NotBlank
	private String email;
	
	private String idioma;	
	
	@NotBlank
	private String login;
	
	@NotBlank
	private String senha;	
	
	private ContaDTO contaDTO;		

}
