package br.com.artsoft.finart.infra.investimento;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.artsoft.finart.infra.investimento.ativo.AtivoClient;
import br.com.artsoft.finart.infra.investimento.ativo.AtivoInfoRequisicao;
import br.com.artsoft.finart.infra.investimento.ativo.AtivoSumarioDTO;
import br.com.artsoft.finart.model.domain.investimento.AtivoRepository;


@Component
public class AtivoRepositoryApi implements AtivoRepository {
	
	@Autowired
	private AtivoClient ativoClient;
	
	@Value("${brapi-api-token}")
	private String token;

	@Override
	public BigDecimal getCotacaoAtualByAtivo(String ativo) {		
		
		AtivoInfoRequisicao retorno = ativoClient.buscarInformacoesAtivo(ativo, token);
		
		Optional<AtivoInfoRequisicao> retornoOp = Optional.of(retorno);
		
		retorno = retornoOp.orElseThrow();	
		
		BigDecimal cotacao = retorno.getResults()
				.stream()
				.findFirst()
				.map(AtivoSumarioDTO::getRegularMarketPrice)
				.get();
		
		return cotacao;
	}

}
