/*
 * C�digo-fonte do livro "Programa��o Java para a Web"
 * Autores: D�cio Heinzelmann Luckow <decioluckow@gmail.com>
 *          Alexandre Altair de Melo <alexandremelo.br@gmail.com>
 *
 * ISBN: 978-85-7522-238-6
 * http://www.javaparaweb.com.br
 * http://www.novatec.com.br/livros/javaparaweb
 * Editora Novatec, 2010 - todos os direitos reservados
 *
 * LICEN�A: Este arquivo-fonte est� sujeito a Atribui��o 2.5 Brasil, da licen�a Creative Commons,
 * que encontra-se dispon�vel no seguinte endere�o URI: http://creativecommons.org/licenses/by/2.5/br/
 * Se voc� n�o recebeu uma c�pia desta licen�a, e n�o conseguiu obt�-la pela internet, por favor,
 * envie uma notifica��o aos seus autores para que eles possam envi�-la para voc� imediatamente.
 *
 *
 * Source-code of "Programa��o Java para a Web" book
 * Authors: D�cio Heinzelmann Luckow <decioluckow@gmail.com>
 *          Alexandre Altair de Melo <alexandremelo.br@gmail.com>
 *
 * ISBN: 978-85-7522-238-6
 * http://www.javaparaweb.com.br
 * http://www.novatec.com.br/livros/javaparaweb
 * Editora Novatec, 2010 - all rights reserved
 *
 * LICENSE: This source file is subject to Attribution version 2.5 Brazil of the Creative Commons
 * license that is available through the following URI:  http://creativecommons.org/licenses/by/2.5/br/
 * If you did not receive a copy of this license and are unable to obtain it through the web, please
 * send a note to the authors so they can mail you a copy immediately.
 *
 */
package br.com.artsoft.finart.controller.rs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.artsoft.finart.controller.util.ContextoUtil;
import br.com.artsoft.finart.model.domain.Acao;
import br.com.artsoft.finart.model.domain.AcaoVirtual;
import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.exception.RNException;
import br.com.artsoft.finart.model.service.AcaoRN;
import br.com.artsoft.finart.model.service.ContextoRN;

@RestController
@RequestMapping("/acao")
public class AcaoRS {

	@Autowired
	ContextoRN contextoRN;
	
	@Autowired
	AcaoRN acaoRN;		

	@PostMapping
	public ResponseEntity<Acao> salvar(@RequestBody AcaoVirtual acaoVirtual) throws Exception {		
		Usuario usuarioLogado = contextoRN.getUsuarioLogado(ContextoUtil.getLoginUsuarioLogado());		
		Acao acao = acaoVirtual.getAcao();
		acao.setSigla(acao.getSigla().toUpperCase());
		acao.setUsuario(usuarioLogado);
		acaoRN.salvar(acao);	
		
		return ResponseEntity.ok(acao);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Acao> excluir(@PathVariable("id") Integer codigo) {	
		
		Acao acao = acaoRN.carregar(codigo);
		acaoRN.excluir(acao);	
		
		return ResponseEntity.ok(acao);
	}

	@GetMapping
	public ResponseEntity<List<AcaoVirtual>> getLista() throws Exception {
		Usuario usuarioLogado = contextoRN.getUsuarioLogado(ContextoUtil.getLoginUsuarioLogado());
		List<AcaoVirtual>	lista;
		try {			
			lista = acaoRN.listarAcaoVirtual(usuarioLogado);			
		} catch (RNException e) {			
			throw new Exception(e.getMessage());	
		}
		return ResponseEntity.ok(lista);
	}

	/*public String getLinkCodigoAcao() {		
		if (this.selecionada != null) {
			this.linkCodigoAcao = acaoRN.montaLinkAcao(this.selecionada.getAcao());
		} else {
			this.linkCodigoAcao = YahooProperties.INDICE_BOVESPA;
		}
		return this.linkCodigoAcao;
	}*/
	
}
