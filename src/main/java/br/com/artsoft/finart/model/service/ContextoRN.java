package br.com.artsoft.finart.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.artsoft.finart.model.domain.Conta;
import br.com.artsoft.finart.model.domain.Usuario;

@Component
public class ContextoRN {
	
	@Autowired
	UsuarioRN usuarioRN;	
	
	@Autowired
	ContaRN contaRN;	
	
	public Usuario getUsuarioLogado(String login) {
		return  usuarioRN.buscarPorEmail(login);
	}
	
	public Conta getContaAtiva(Usuario usuario) {	
		
		Conta contaAtiva = contaRN.buscarFavorita(usuario);

		if (contaAtiva == null) {
			List<Conta> contas = contaRN.listar(usuario);
			if (contas != null) {
				for (Conta conta : contas) {
					contaAtiva = conta;
					break;
				}
			}
		}
		return contaAtiva;
	}

}
