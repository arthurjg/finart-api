/*
 * Código-fonte do livro "Programação Java para a Web"
 * Autores: Décio Heinzelmann Luckow <decioluckow@gmail.com>
 *          Alexandre Altair de Melo <alexandremelo.br@gmail.com>
 *
 * ISBN: 978-85-7522-238-6
 * http://www.javaparaweb.com.br
 * http://www.novatec.com.br/livros/javaparaweb
 * Editora Novatec, 2010 - todos os direitos reservados
 *
 * LICENÇA: Este arquivo-fonte está sujeito a Atribuição 2.5 Brasil, da licença Creative Commons,
 * que encontra-se disponível no seguinte endereço URI: http://creativecommons.org/licenses/by/2.5/br/
 * Se você não recebeu uma cópia desta licença, e não conseguiu obtê-la pela internet, por favor,
 * envie uma notificação aos seus autores para que eles possam enviá-la para você imediatamente.
 *
 *
 * Source-code of "Programação Java para a Web" book
 * Authors: Décio Heinzelmann Luckow <decioluckow@gmail.com>
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
package financeiro.rn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import financeiro.dao.AcaoDAO;
import financeiro.model.Acao;
import financeiro.model.AcaoVirtual;
import financeiro.model.Usuario;
import financeiro.util.DAOFactory;
import financeiro.util.RNException;
import financeiro.web.util.YahooFinanceUtil;

public class AcaoRN {

	private AcaoDAO	acaoDAO;

	public AcaoRN() {
		this.acaoDAO = DAOFactory.criarAcaoDAO();
	}

	public void salvar(Acao acao) {
		this.acaoDAO.salvar(acao);
	}

	public void excluir(Acao acao) {
		this.acaoDAO.excluir(acao);
	}

	public Acao carregar(String codigo) {
		return this.acaoDAO.carregar(codigo);
	}

	public List<Acao> listar(Usuario usuario) {
		return this.acaoDAO.listar(usuario);
	}

	public List<AcaoVirtual> listarAcaoVirtual(Usuario usuario) throws RNException {
		List<Acao> listaAcao = null;
		List<AcaoVirtual> listaAcaoVirtual = new ArrayList<AcaoVirtual>();
		AcaoVirtual acaoVirtual = null;
		String cotacao = null;
		float ultimoPreco = 0.0f;
		float total = 0.0f;
		int quantidade = 0;

		try {
			listaAcao = this.listar(usuario);
			for (Acao acao : listaAcao) {
				acaoVirtual = new AcaoVirtual();
				acaoVirtual.setAcao(acao);
				cotacao = this.retornaCotacao(YahooFinanceUtil.ULTIMO_PRECO_DIA_ACAO_INDICE, acao);
				if (cotacao != null) {
					ultimoPreco = new Float(cotacao).floatValue();
					quantidade = acao.getQuantidade();
					total = ultimoPreco * quantidade;
					acaoVirtual.setUltimoPreco(ultimoPreco);
					acaoVirtual.setTotal(total);
					listaAcaoVirtual.add(acaoVirtual);
				}
			}
		} catch (RNException e) {
			throw new RNException("Não foi possível listar ações. Erro: " + e.getMessage());
		}
		return listaAcaoVirtual;
	}

	public String retornaCotacao(int indiceInformacao, Acao acao) throws RNException {
		YahooFinanceUtil yahooFinanceUtil = null;
		String informacao = null;
		try {
			yahooFinanceUtil = new YahooFinanceUtil(acao);
			informacao = yahooFinanceUtil.retornaCotacao(indiceInformacao, acao.getSigla());
		} catch (IOException e) {
			throw new RNException("Não foi possível recuperar cotação. Erro: " + e.getMessage());
		}
		return informacao;
	}

	public String montaLinkAcao(Acao acao) {
		String link = null;
		if (acao != null) {
			if (acao.getOrigem() != null) {
				if (acao.getOrigem() == YahooFinanceUtil.ORIGEM_BOVESPA) {
					link = acao.getSigla() + YahooFinanceUtil.POSFIXO_ACAO_BOVESPA;
				} else {
					link = acao.getSigla();
				}
			} else {
				link = YahooFinanceUtil.INDICE_BOVESPA;
			}
		} else {
			link = YahooFinanceUtil.INDICE_BOVESPA;
		}
		return link;
	}
}
