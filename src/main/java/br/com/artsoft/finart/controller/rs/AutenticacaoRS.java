package br.com.artsoft.finart.controller.rs;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.artsoft.finart.controller.dto.AutorizacaoDTO;
import br.com.artsoft.finart.controller.util.ContextoUtil;
import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.service.UsuarioRN;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/autenticacao")
@RequiredArgsConstructor
public class AutenticacaoRS {
	
	private final UsuarioRN usuarioRN;	
	
	@PostMapping
	public ResponseEntity<Object> login(@RequestBody @Validated AutorizacaoDTO autorizacao){
		
		Optional<Usuario> usarioLogin = usuarioRN.buscarPorEmailESenha(autorizacao.getEmail(), autorizacao.getSenha());
		if(usarioLogin.isPresent()) {
			
			Map<String, String> token = ContextoUtil.createToken(usarioLogin.get());
			
			return ResponseEntity.ok(token);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}		
	}

}
