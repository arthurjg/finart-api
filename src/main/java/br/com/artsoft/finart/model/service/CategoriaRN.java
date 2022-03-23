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
package br.com.artsoft.finart.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.artsoft.finart.model.domain.Categoria;
import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.repository.CategoriaRepository;

public class CategoriaRN {
	
	@Autowired	
	private  CategoriaRepository	categoriaRepository;

	public CategoriaRN() {		
	}

	public CategoriaRN(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}

	public List<Categoria> listar(Usuario usuario) {
		return this.categoriaRepository.listar(usuario);
	}

	
	public Categoria salvar(Categoria categoria) {
		Categoria pai = categoria.getPai();

		if (pai == null) {
			String msg = "A Categoria " + categoria.getDescricao() + " deve ter um pai definido";
			throw new IllegalArgumentException(msg);
		}

		boolean mudouFator = pai.getFator() != categoria.getFator();

		categoria.setFator(pai.getFator());
		categoria = this.categoriaRepository.salvar(categoria);

		if (mudouFator) {
			categoria = this.carregar(categoria.getCodigo());
			this.replicarFator(categoria, categoria.getFator());
		}

		return categoria;
	}

	private void replicarFator(Categoria categoria, int fator) {
		if (categoria.getFilhos() != null) {
			for (Categoria filho : categoria.getFilhos()) {
				filho.setFator(fator);
				this.categoriaRepository.salvar(filho);
				this.replicarFator(filho, fator);
			}
		}
	}
	
	
	public void excluir(Categoria categoria) {

		//OrcamentoRN orcamentoRN = new OrcamentoRN();
		//orcamentoRN.excluir(categoria);

		this.categoriaRepository.excluir(categoria);
	}
	
	
	public void excluir(Usuario usuario) {
		List<Categoria> lista = this.listar(usuario);
		for (Categoria categoria:lista) {
			this.categoriaRepository.excluir(categoria);
		}
	}

	public Categoria carregar(Integer categoria) {
		return this.categoriaRepository.carregar(categoria);
	}
	
	public List<Integer> carregarCodigos(Integer categoria) {
		List<Integer> codigos = new ArrayList<Integer>();
		
		Categoria c = this.carregar(categoria);
		this.extraiCodigos(codigos, c);
		
		return codigos;
	}
	
	private void extraiCodigos(List<Integer> codigos, Categoria categoria) {
		codigos.add(categoria.getCodigo());
		if (categoria.getFilhos() != null) {
			for (Categoria filho:categoria.getFilhos()) {
				this.extraiCodigos(codigos, filho);
			}
		}
	}

	
	public void salvaEstruturaPadrao(Usuario usuario) {

		Categoria despesas = new Categoria(null, usuario, "DESPESAS", -1);
		despesas = this.categoriaRepository.salvar(despesas);
		this.categoriaRepository.salvar(new Categoria(despesas, usuario, "Moradia", -1));
		this.categoriaRepository.salvar(new Categoria(despesas, usuario, "Alimenta��o", -1));
		this.categoriaRepository.salvar(new Categoria(despesas, usuario, "Vestu�rio", -1));
		this.categoriaRepository.salvar(new Categoria(despesas, usuario, "Deslocamento", -1));
		this.categoriaRepository.salvar(new Categoria(despesas, usuario, "Cuidados Pessoais", -1));
		this.categoriaRepository.salvar(new Categoria(despesas, usuario, "Educa��o", -1));
		this.categoriaRepository.salvar(new Categoria(despesas, usuario, "Sa�de", -1));
		this.categoriaRepository.salvar(new Categoria(despesas, usuario, "Lazer", -1));
		this.categoriaRepository.salvar(new Categoria(despesas, usuario, "Despesas Financeiras", -1));

		Categoria receitas = new Categoria(null, usuario, "RECEITAS", 1);
		receitas = this.categoriaRepository.salvar(receitas);
		this.categoriaRepository.salvar(new Categoria(receitas, usuario, "Sal�rio", 1));
		this.categoriaRepository.salvar(new Categoria(receitas, usuario, "Restitui��es", 1));
		this.categoriaRepository.salvar(new Categoria(receitas, usuario, "Rendimento", 1));
	}
}