package br.com.artsoft.finart.controller.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.artsoft.finart.controller.util.ContextoUtil;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

	private static final String HEADER = "Authorization";
	private static final String PREFIX = "Bearer ";	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
	   handlerResponseForCrossOrigen(response);
		if (request.getMethod().equals("OPTIONS")) {
			  response.setStatus(HttpServletResponse.SC_OK);
		} else {
			try {
				checkToken(request);
				chain.doFilter(request, response);
			} catch (IOException e) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			}
		}
	}

	private void checkToken(HttpServletRequest request) throws IOException {
		if (checkJWTToken(request)) {
			Map<String, Object> claims = validateAndExtractToken(request);
			if(!claims.isEmpty()) {
				handlerUserNamePassordAuthenticationToken(claims);
			} else {
				SecurityContextHolder.clearContext();
			}
		} else {
			SecurityContextHolder.clearContext();
		}
	}

	private void handlerUserNamePassordAuthenticationToken(Map<String, Object> claims) {
		@SuppressWarnings("unchecked")
		List<String> authorities = (claims.get("authorities") != null) ? (List<String>) claims.get("authorities") : new ArrayList<>();
		
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(claims, null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())));
	}

	private void handlerResponseForCrossOrigen(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token");
		response.addHeader("Access-Control-Expose-Headers", "xsrf-token");
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> validateAndExtractToken(HttpServletRequest request) throws IOException {
		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");	
		
		ContextoUtil.validateToken(jwtToken);
		
		var payload = jwtToken.substring(jwtToken.indexOf("."), jwtToken.lastIndexOf("."));
		var objectMapper = new ObjectMapper();
		return objectMapper.readValue(Base64.decodeBase64(payload), Map.class);
	}

	private boolean checkJWTToken(HttpServletRequest request) {
		String authenticationHeader = request.getHeader(HEADER);
		return !(authenticationHeader == null || !authenticationHeader.startsWith(PREFIX));
	}

}
