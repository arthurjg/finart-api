package br.com.artsoft.finart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.artsoft.finart.controller.security.JWTAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JWTAuthorizationFilter jwtAuthFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {		
		http.csrf().disable().formLogin().disable()
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers("/api/**", "/teste/", "/swagger*/**", "/webjars/**", "/usuarios/registro")
				.permitAll()
				.anyRequest().authenticated();
		
	}
	
	

}
