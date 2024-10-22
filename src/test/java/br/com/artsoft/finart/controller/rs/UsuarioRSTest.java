package br.com.artsoft.finart.controller.rs;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.artsoft.finart.controller.dto.UsuarioDTO;
import br.com.artsoft.finart.model.service.UsuarioRN;

/*
@AutoConfigureMockMvc



@SpringBootTest

*/


@WebMvcTest(UsuarioRS.class)
@AutoConfigureJsonTesters
class UsuarioRSTest {	
	
	@Autowired
	MockMvc mockMvc;
	
	/*@Autowired
	WebApplicationContext context;*/
	
	@MockBean
	UsuarioRN usuarioRN;
	
	@Autowired
	private JacksonTester<UsuarioDTO> dtoMediaConverter;

	/*@BeforeEach
	void setUp() throws Exception {		
		
		this.mockMvc = 
				MockMvcBuilders.webAppContextSetup(context)
				.apply(springSecurity())				
				.build();
				//MockMvcBuilders.standaloneSetup(new UsuarioRS()).build();
	}*/

	/*@Test	
	void testSalvar() throws IOException, Exception {
		
		UsuarioDTO param = UsuarioDTO.builder()
				.nome("Mario")
				.email("mario@gmail.com")
				.login("mario")
				.senha("ma45678rio")
				.idioma("PT-BR")
				.nascimento(LocalDate.of(2000, 5, 5))
				.build();
		
		//given(usuarioRN.salvar(null)).
		
		mockMvc.perform(
				post("/usuarios/registro")					
					.contentType(MediaType.APPLICATION_JSON)						
					.content(dtoMediaConverter.write(param).getJson())
				)		
			.andExpect(status().isCreated());
	}*/

}
