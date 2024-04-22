package br.com.artsoft.finart.model.investimento.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.repository.UsuarioRepository;

@SpringBootTest
class UsuarioRepositoryIntegrationTest {
	
	@Autowired
	UsuarioRepository usuarioRepository;	

	@Test
	void testSaveUsuario() {
		
		Usuario usuario = Usuario.builder()
				.nome("Mario")
				.login("mario")
				.email("mario@gmail.com")
				.idioma("EN")
				.nascimento(LocalDate.of(2000, 5, 5))
				.senha("ma45678rio")
				.celular("48999887766")
				.ativo(true)			
				.build();		
		
		usuarioRepository.save(usuario);
		
		assertNotNull(usuario.getCodigo());		
	}

}
