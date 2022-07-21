package br.com.artsoft.finart.controller.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.artsoft.finart.controller.dto.AutorizacaoDTO;
import br.com.artsoft.finart.model.domain.Usuario;

public class ContextoUtil {
	
	public static final String TOKEN_KEY = "access_token";
	
	public static String getLoginUsuarioLogado() {
		var token = getTokenPayload();
		if(token != null) {
			return (String) token.get("nome");
		}
		return "";
	}
	
	public static Map<String, String> createToken(Usuario usuario) {
		Map<String, Object> payload = createTokenPayload(usuario);
		
		Algorithm algorithm = Algorithm.HMAC256("secret");
		String jwtToken = JWT.create()
				.withPayload(payload)
				.sign(algorithm);
		
		Map<String, String> response = new HashMap<>();
		response.put(TOKEN_KEY, jwtToken);
		
		return response;
	}
	
	private static Map<String, Object> createTokenPayload(Usuario usuario) {
		Map<String, Object> payload = new HashMap<>();
		payload.put("codigo", usuario.getCodigo());
		payload.put("nome", usuario.getNome());
		payload.put("email", usuario.getEmail());
		
		return payload;
	}
	
	private static Map<String, Object> getTokenPayload() {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.getPrincipal() != null) {
			return (Map<String, Object>) authentication.getPrincipal();
		}
		return null;
	}
	
	

}
