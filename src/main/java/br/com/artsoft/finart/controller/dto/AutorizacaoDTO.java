package br.com.artsoft.finart.controller.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AutorizacaoDTO {
	
	@Email
	@NotBlank
	private String email;	
	
	@NotBlank
	private String senha;	

}
