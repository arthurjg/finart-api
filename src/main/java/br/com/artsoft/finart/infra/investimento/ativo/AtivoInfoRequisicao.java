package br.com.artsoft.finart.infra.investimento.ativo;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AtivoInfoRequisicao {
	
	List<AtivoSumarioDTO> results;
	
	LocalDateTime requestedAt;

}
