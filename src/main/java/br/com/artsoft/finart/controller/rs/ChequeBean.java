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

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.web.bind.annotation.RestController;

import br.com.artsoft.finart.controller.rs.util.ContextoUtil;
import br.com.artsoft.finart.controller.rs.util.MensagemUtil;
import br.com.artsoft.finart.model.domain.Cheque;
import br.com.artsoft.finart.model.domain.Conta;
import br.com.artsoft.finart.model.exception.RNException;
import br.com.artsoft.finart.model.service.ChequeRN;

@RestController
public class ChequeBean {

	private Cheque				selecionado	= new Cheque();
	private List<Cheque>	lista				= null;
	private Integer				chequeInicial;
	private Integer				chequeFinal;

	public void salvar() {
		FacesContext context = FacesContext.getCurrentInstance();
		ContextoBean contextoBean = ContextoUtil.getContextoBean();
		Conta conta = contextoBean.getContaAtiva();
		
		int totalCheques = 0;
		if (this.chequeInicial == null || this.chequeFinal == null) {
			String texto = MensagemUtil.getMensagem("cheque_erro_sequencia");
			FacesMessage msg = new FacesMessage(texto);
			context.addMessage(null, msg);
		} else if (this.chequeFinal.intValue() < this.chequeInicial.intValue()) {
			String texto = MensagemUtil.getMensagem("cheque_erro_inicial_final", this.chequeInicial, this.chequeFinal);
			FacesMessage msg = new FacesMessage(texto);
			context.addMessage(null, msg);
		} else {
			ChequeRN chequeRN = new ChequeRN();
			totalCheques = chequeRN.salvarSequencia(conta, this.chequeInicial, this.chequeFinal);
			String texto = MensagemUtil.getMensagem("cheque_info_cadastro", this.chequeFinal, totalCheques);
			FacesMessage msg = new FacesMessage(texto);
			context.addMessage(null, msg);
			this.lista = null;
		}
	}

	public void excluir() {
		ChequeRN chequeRN = new ChequeRN();
		try {
			chequeRN.excluir(this.selecionado);
		} catch (RNException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			String texto = MensagemUtil.getMensagem("cheque_erro_excluir");
			FacesMessage msg = new FacesMessage(texto);
			msg.setSeverity(FacesMessage.SEVERITY_WARN);
			context.addMessage(null, msg);
		}
		this.lista = null;
	}

	public void cancelar() {
		ChequeRN chequeRN = new ChequeRN();
		try {
			chequeRN.cancelarCheque(this.selecionado);
		} catch (RNException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			String texto = MensagemUtil.getMensagem("cheque_erro_cancelar");
			FacesMessage msg = new FacesMessage(texto);
			msg.setSeverity(FacesMessage.SEVERITY_WARN);
			context.addMessage(null, msg);
		}
		this.lista = null;
	}

	public List<Cheque> getLista() {
		if (this.lista == null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			Conta conta = contextoBean.getContaAtiva();

			ChequeRN chequeRN = new ChequeRN();
			this.lista = chequeRN.listar(conta);
		}
		return this.lista;
	}

	public Cheque getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Cheque selecionado) {
		this.selecionado = selecionado;
	}

	public Integer getChequeInicial() {
		return chequeInicial;
	}

	public void setChequeInicial(Integer chequeInicial) {
		this.chequeInicial = chequeInicial;
	}

	public Integer getChequeFinal() {
		return chequeFinal;
	}

	public void setChequeFinal(Integer chequeFinal) {
		this.chequeFinal = chequeFinal;
	}
}
