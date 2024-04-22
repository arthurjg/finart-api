package br.com.artsoft.finart.controller.rs;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.artsoft.finart.config.SecurityConfig;
import br.com.artsoft.finart.controller.dto.UsuarioDTO;
import br.com.artsoft.finart.controller.dto.UsuarioMapper;
import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.service.UsuarioRN;

@RestController
@RequestMapping("/usuarios")
public class UsuarioRS {
	
	@Autowired
	UsuarioRN usuarioRN;		
	
	@Autowired
	SecurityConfig security;
	
	@PostMapping("/registro")
	public ResponseEntity<Usuario> salvar(@RequestBody @Validated UsuarioDTO usuario) throws Exception {				
		
		Usuario usuarioNovo = UsuarioMapper.map(usuario);
		
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String senhaCodificada = encoder.encode(usuarioNovo.getSenha());
		
		usuarioNovo.setSenha(senhaCodificada);
		usuarioRN.salvar(usuarioNovo);
		
		/**
		 * TODO alterar logica para usar ContaRS
		 
		if (conta.getDescricao() != null) {
			conta.setUsuario(usuarioNovo);
			conta.setFavorita(true);
			ContaRN contaRN = new ContaRN();
			contaRN.salvar(conta);
		}*/		
		
		return ResponseEntity.created(URI.create("/usuarios/" + usuarioNovo.getCodigo())).build();
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Usuario> excluir(@PathVariable("id") Integer codigo) {
		
		Usuario usuario = usuarioRN.carregar(codigo);
		
		if(Objects.isNull(usuario)) {
			return ResponseEntity.notFound().build();
		}
		
		usuarioRN.excluir(usuario);
		
		return ResponseEntity.ok(usuario);
	}

	@PutMapping("/{id}/ativacao")
	public ResponseEntity<Usuario> ativar(@PathVariable("id") Integer codigo) {
		
		Usuario usuario = usuarioRN.carregar(codigo);
		if(Objects.isNull(usuario)) {
			return ResponseEntity.notFound().build();
		}
		
		if (usuario.isAtivo())
			usuario.setAtivo(false);
		else
			usuario.setAtivo(true);
		
		usuarioRN.salvar(usuario);
		
		return ResponseEntity.ok(usuario);
	}
	
	@PutMapping("/{id}/permissao/{permissao}")
	public ResponseEntity<Usuario> atribuiPermissao(@PathVariable("id") Integer codigo, 
			@PathVariable("permissao") String permissao) {

		Usuario usuario = usuarioRN.carregar(codigo);
		if(Objects.isNull(usuario)) {
			return ResponseEntity.notFound().build();
		}

		Set<String> permissoes = usuario.getPermissoes()
				.stream()
				.map(p -> p.getNome())
				.collect(Collectors.toSet());

		if (permissoes.contains(permissao)) {
			permissoes.remove(permissao);
		} else {
			permissoes.add(permissao);
		}
		return ResponseEntity.ok(usuario);
	}
	
	@GetMapping
	public ResponseEntity<List<Usuario>> getLista() {
		List<Usuario> lista = usuarioRN.listar();		
		return ResponseEntity.ok(lista);
	}	

}
