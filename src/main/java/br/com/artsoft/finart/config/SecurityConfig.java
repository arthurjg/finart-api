package br.com.artsoft.finart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.artsoft.finart.controller.security.JWTAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {	
	
	@Autowired
	private JWTAuthorizationFilter jwtFilter;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http.csrf(csrf -> csrf.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests(requests -> {
                	requests.antMatchers(HttpMethod.POST, "/autenticacao").permitAll();
                	requests.antMatchers(HttpMethod.POST, "/usuarios/registro").permitAll();
                	requests.anyRequest().authenticated();
                })
                .build();
	}

	
	@Bean
	AuthenticationManager getAuthenticationManager(AuthenticationConfiguration configuration ) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
