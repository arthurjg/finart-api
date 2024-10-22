package br.com.artsoft.finart.controller.rs.investimento;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import br.com.artsoft.finart.controller.investimento.dto.InvestimentoDetalhesDTO;
import br.com.artsoft.finart.controller.investimento.mapper.InvestimentoMapper;
import br.com.artsoft.finart.model.service.ContextoRN;
import br.com.artsoft.finart.model.service.investimento.InvestimentoService;

/*

@ExtendWith(SpringExtension.class)
@ContextConfiguration



@SpringBootTest

@AutoConfigureMockMvc
*/


@WebMvcTest(InvestimentoRS.class)
@AutoConfigureJsonTesters
class InvestimentoRSTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ContextoRN contextoRN;	
	
	@MockBean
	private InvestimentoService investimentoRN;	
	
	@MockBean
	private InvestimentoMapper investimentoMapper;
	
	@Autowired
	private JacksonTester<InvestimentoDetalhesDTO> investimentoDtomediaConverter;		

	/* TODO RESOLVER PROBLEMA DE CONFIG */
	//@Test	
	@WithMockUser("user")	
	//@WithMockUser(username="joao@gmail.com", password="joao12345", roles={"USUARIO"})	
	//@WithUserDetails("joao@gmail.com")	
	//@WithAnonymousUser
	void testSalvarDeveriaSalvarNovoInvestimento() throws Exception {
		
		InvestimentoDetalhesDTO param = InvestimentoDetalhesDTO.builder()
				.nome("LCI")
				.tipo("1")
				.natureza("RF")
				.build();		
		
		mockMvc.perform(
				post("/investimentos")					
					.contentType(MediaType.APPLICATION_JSON)	
					//.header("Authorization", "Bearer mock.aaaaaa.bbbbb")
					.content(investimentoDtomediaConverter.write(param).getJson())
				)
		
			.andExpect(status().isCreated());
	}
	
	@Test
	@WithMockUser("john")
	void testListar_DeveriaListarInvestimentos() throws Exception {
				
		mockMvc.perform(get("/investimentos"))
			.andExpect(status().isOk());
	}
	
	//@Test	
	void testSalvar_DeveriaRetornarErro401() throws Exception {
		
		String param = "";
		
		mockMvc.perform(post("/investimentos").content(param))
			.andExpect(status().isUnauthorized());
	}
	
	//@Test
	@WithMockUser("john")
	void testSalvar_DeveriaRetornarErro400() throws Exception {
			
		String param = "";
			
		mockMvc.perform(post("/investimentos").content(param))
			.andExpect(status().isBadRequest());
	}

}
