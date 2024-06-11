package br.com.artsoft.finart.model.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.artsoft.finart.model.domain.Permissao;
import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.repository.PermissaoRepository;
import br.com.artsoft.finart.model.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioRNTest {
	
	@InjectMocks
	UsuarioRN usuarioRN;
	
	@Mock
	UsuarioRepository usuarioRepo;
	
	@Mock
	PermissaoRepository permissaoRepository;
	
	@Mock
	CategoriaRN categoriaRN;	

	@Test
	void testSalvar_Sucesso() {
		
		Usuario usuario = Usuario.builder()
		.nome("John")
		.login("john")
		.email("johnn@gmail.com")
		.idioma("EN")
		.nascimento(LocalDate.of(2000, 5, 5))
		.senha("12345")
		.celular("48999887766")
		.ativo(true)			
		.build();	
		
		String permissaoBasica = "ROLE_USUARIO";
		
		Permissao permissao = new Permissao(permissaoBasica);
		
		when(permissaoRepository.findByNome(permissaoBasica)).thenReturn(permissao);
		
		usuarioRN.salvar(usuario);
		
		verify(usuarioRepo).save(usuario);
		//verify(categoriaRN).salvaEstruturaPadrao(usuario);
		assertFalse(usuario.getPermissoes().isEmpty());
	}

}
