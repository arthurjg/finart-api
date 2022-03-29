package br.com.artsoft.finart.controller.util;

import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;

public class ContextoUtil {
	
	public static String getLoginUsuarioLogado() {
		var token = getTokenPayload();
		if(token != null) {
			return (String) token.get("nome");
		}
		return "";
	}
	
	private static Map<String, Object> getTokenPayload() {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.getPrincipal() != null) {
			return (Map<String, Object>) authentication.getPrincipal();
		}
		return null;
	}

}
