package br.com.artsoft.finart.infra.investimento.ativo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "AtivoAPIClient", url = "${brapi-api-url}")
public interface AtivoClient {
	
	@GetMapping(value = "/quote/{codigo}")
	AtivoInfoRequisicao buscarInformacoesAtivo(
			@PathVariable String codigo, @RequestHeader("Authorization") String token);

}
