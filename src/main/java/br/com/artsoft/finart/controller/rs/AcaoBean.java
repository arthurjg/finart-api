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
import br.com.artsoft.finart.controller.rs.util.YahooFinanceUtil;
import br.com.artsoft.finart.model.domain.Acao;
import br.com.artsoft.finart.model.domain.AcaoVirtual;
import br.com.artsoft.finart.model.exception.RNException;
import br.com.artsoft.finart.model.service.AcaoRN;

@RestController
public class AcaoBean {

	private AcaoVirtual			selecionada		= new AcaoVirtual();
	private List<AcaoVirtual>	lista				= null;
	private String					linkCodigoAcao	= null;

	public void salvar() {
		ContextoBean contextoBean = ContextoUtil.getContextoBean();
		AcaoRN acaoRN = new AcaoRN();
		Acao acao = this.selecionada.getAcao();
		acao.setSigla(acao.getSigla().toUpperCase());
		acao.setUsuario(contextoBean.getUsuarioLogado());
		acaoRN.salvar(acao);
		this.selecionada = new AcaoVirtual();
		this.lista = null;
	}

	public void excluir() {
		AcaoRN acaoRN = new AcaoRN();
		acaoRN.excluir(this.selecionada.getAcao());
		this.selecionada = new AcaoVirtual();
		this.lista = null;
	}

	public List<AcaoVirtual> getLista() {
		FacesContext context = FacesContext.getCurrentInstance();
		ContextoBean contextoBean = ContextoUtil.getContextoBean();
		AcaoRN acaoRN = new AcaoRN();
		try {
			if (this.lista == null) {
				this.lista = acaoRN.listarAcaoVirtual(contextoBean.getUsuarioLogado());
			}
		} catch (RNException e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));
		}
		return this.lista;
	}

	public String getLinkCodigoAcao() {
		AcaoRN acaoRN = new AcaoRN();
		if (this.selecionada != null) {
			this.linkCodigoAcao = acaoRN.montaLinkAcao(this.selecionada.getAcao());
		} else {
			this.linkCodigoAcao = YahooFinanceUtil.INDICE_BOVESPA;
		}
		return this.linkCodigoAcao;
	}

	public AcaoVirtual getSelecionada() {
		return this.selecionada;
	}

	public void setLinkCodigoAcao(String linkCodigoAcao) {
		this.linkCodigoAcao = linkCodigoAcao;
	}

	public void setLista(List<AcaoVirtual> lista) {
		this.lista = lista;
	}

	public void setSelecionada(AcaoVirtual selecionada) {
		this.selecionada = selecionada;
	}
}
