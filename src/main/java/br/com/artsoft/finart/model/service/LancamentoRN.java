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

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.artsoft.finart.model.domain.Conta;
import br.com.artsoft.finart.model.domain.Lancamento;
import br.com.artsoft.finart.model.repository.LancamentoRepository;


public class LancamentoRN {
	
	@Autowired
	private LancamentoRepository	lancamentoRepository;

	public LancamentoRN() {		
	}
	
	@Transactional
	public void salvar(Lancamento lancamento) {
		this.lancamentoRepository.salvar(lancamento);
	}
	
	@Transactional
	public void atualizar(Lancamento lancamento) {
		this.lancamentoRepository.atualizar(lancamento);
	}
	
	@Transactional
	public void excluir(Lancamento lancamento) {
		this.lancamentoRepository.excluir(lancamento);
	}
	
	@Transactional
	public Lancamento carregar(Integer lancamento) {
		return this.lancamentoRepository.carregar(lancamento);
	}
	
	@Transactional
	public float saldo(Conta conta, Date data) { 
		float saldoInicial = conta.getSaldoInicial();
		float saldoNaData = this.lancamentoRepository.saldo(conta, data);
		return saldoInicial + saldoNaData;
	}
	
	@Transactional
	public List<Lancamento> listar(Conta conta, Date dataInicio, Date dataFim) { 
		return this.lancamentoRepository.listar(conta, dataInicio, dataFim);
	}
}
