package br.com.artsoft.finart.model.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.stereotype.Component;

import br.com.artsoft.finart.model.domain.Acao;
import br.com.artsoft.finart.model.service.yahoofinance.YahooProperties;

@Component
public class YahooFinanceComponent {
	
	private String							local;
	private String[]						informacoesCotacao;		

	public String retornaCotacao(Acao acao, int indiceInformacao, String nomeAcao) throws IOException {
		if (acao.getOrigem() == YahooProperties.ORIGEM_BOVESPA) {
			this.local = YahooProperties.LOCAL_BOVESPA;
		} else {
			this.local = YahooProperties.LOCAL_MUNDO;
		}
		
		if (this.local == YahooProperties.LOCAL_BOVESPA) {
			nomeAcao = nomeAcao + YahooProperties.POSFIXO_ACAO_BOVESPA;
		}

		if ((indiceInformacao > 8) || (indiceInformacao < 0)) {
			indiceInformacao = YahooProperties.ULTIMO_PRECO_DIA_ACAO_INDICE;
		}

		String endereco = "http://" + this.local + ".finance.yahoo.com/d/quotes.csv?s=" + nomeAcao + "&f=sl1d1t1c1ohgv&e=.csv";
		String linha = null;
		URL url = null;
		String valorRetorno = null;

		try {
			url = new URL(endereco);
			URLConnection conexao = url.openConnection();						
			
			InputStreamReader conteudo = new InputStreamReader(conexao.getInputStream());
			BufferedReader arquivo = new BufferedReader(conteudo);

			while ((linha = arquivo.readLine()) != null) {
				linha = linha.replace("\"", "");
				this.informacoesCotacao = linha.split("[" + YahooProperties.SEPARADOR_BOVESPA + YahooProperties.SEPARADOR_MUNDO + "]");
			}
			arquivo.close();
			valorRetorno = this.informacoesCotacao[indiceInformacao];
		} catch (MalformedURLException e) {
			throw new MalformedURLException("URL Inválida. Erro: " + e.getMessage());
		} catch (IOException e) {
			throw new IOException("Problema de escrita e ou leitura. Erro: " + e.getMessage());
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Não existe o índice informado no array. Erro: " + e.getMessage());
		}
		return valorRetorno;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String[] getInformacoesCotacao() {
		return informacoesCotacao;
	}

	public void setInformacoesCotacao(String[] informacoesCotacao) {
		this.informacoesCotacao = informacoesCotacao;
	}

}
