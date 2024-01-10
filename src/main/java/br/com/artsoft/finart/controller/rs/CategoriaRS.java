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
import java.util.Objects;

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
import br.com.artsoft.finart.model.domain.Categoria;
import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.service.CategoriaRN;
import br.com.artsoft.finart.model.service.ContextoRN;

@RestController
@RequestMapping("/categoria")
public class CategoriaRS {

	@Autowired
	CategoriaRN categoriaRN;	
	
	@Autowired
	ContextoRN contextoRN;	

	@PostMapping
	public ResponseEntity<Categoria> salvar(@RequestBody Categoria categoria) {
		
		Categoria pai = null;
		if (categoria.getPai() != null) {			
			pai = categoriaRN.carregar(categoria.getPai().getCodigo());
		}
		
		categoria.setPai(pai);		
		
		Usuario usuarioLogado = contextoRN.getUsuarioLogado(ContextoUtil.getEmailUsuarioLogado());
		
		categoria.setUsuario(usuarioLogado);
		categoriaRN.salvar(categoria);
		
		return ResponseEntity.ok(categoria);
		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Categoria> excluir(@PathVariable("id") Integer codigo) {		
		
		Categoria categoria = categoriaRN.carregar(codigo);
		
		if(Objects.isNull(categoria)) {
			return ResponseEntity.notFound().build();
		}
		
		categoriaRN.excluir(categoria);	
		
		return ResponseEntity.ok(categoria);		
	}

	@GetMapping
	public ResponseEntity<List<Categoria>> getCategoriasTree() {
		
		Usuario usuarioLogado = contextoRN.getUsuarioLogado(ContextoUtil.getEmailUsuarioLogado());
		
		List<Categoria> categorias = categoriaRN.listar(usuarioLogado);		
		
		return ResponseEntity.ok(categorias);		
	}

	/**
	 * TODO REMOVER APOS CONSTRUIR FRONT
	 * 
	 * private void montaDadosTree(TreeNode pai, List<Categoria> lista) {
	 
		if (lista != null && lista.size() > 0) {
			TreeNode filho = null;
			for (Categoria categoria : lista) {
				filho = new DefaultTreeNode(categoria, pai);
				this.montaDadosTree(filho, categoria.getFilhos());
			}
		}
	}

	public List<SelectItem> getCategoriasSelect() {
		if (this.categoriasSelect == null) {
			this.categoriasSelect = new ArrayList<SelectItem>();
			ContextoBean contextoBean = ContextoUtil.getContextoBean();

			CategoriaRN categoriaRN = new CategoriaRN();
			List<Categoria> categorias = categoriaRN.listar(contextoBean.getUsuarioLogado());
			this.montaDadosSelect(this.categoriasSelect, categorias, "");
		}
		return categoriasSelect;
	}

	private void montaDadosSelect(List<SelectItem> select, List<Categoria> categorias, String prefixo) {

		SelectItem item = null;
		if (categorias != null) {
			for (Categoria categoria : categorias) {
				item = new SelectItem(categoria, prefixo + categoria.getDescricao());
				item.setEscape(false);
				select.add(item);
				this.montaDadosSelect(select, categoria.getFilhos(), prefixo + "&nbsp;&nbsp;");
			}
		}
	}
	
	*/
	
}
