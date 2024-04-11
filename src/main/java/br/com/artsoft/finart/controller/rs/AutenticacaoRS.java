package br.com.artsoft.finart.controller.rs;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.artsoft.finart.controller.dto.AutorizacaoDTO;
import br.com.artsoft.finart.controller.util.ContextoUtil;
import br.com.artsoft.finart.infra.usuario.UsuarioDetailsMapper;
import br.com.artsoft.finart.model.domain.Usuario;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/autenticacao")
@RequiredArgsConstructor
public class AutenticacaoRS {		
	
	private final AuthenticationManager manager;	
	
	private final UsuarioDetailsMapper usuarioMapper;
	
	@PostMapping
	public ResponseEntity<Object> login(@RequestBody @Validated AutorizacaoDTO autorizacao){
		
		var tokenParam = new UsernamePasswordAuthenticationToken(autorizacao.getEmail(), autorizacao.getSenha());
		var authentication = manager.authenticate(tokenParam);		
		
		Usuario usarioLogin = usuarioMapper.mapTo((User) authentication.getPrincipal());
		Map<String, String> token = ContextoUtil.createToken(usarioLogin);
			
		return ResponseEntity.ok(token);				
	}

}
