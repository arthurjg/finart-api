
package br.com.artsoft.finart.model.service.investimento;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.artsoft.finart.model.domain.investimento.InvestimentoTipo;
import br.com.artsoft.finart.model.repository.InvestimentoTipoRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InvestimentoTipoService {	
	
	private final InvestimentoTipoRepository investimentoTipoRepository;	

	public List<InvestimentoTipo> listar() {
		return investimentoTipoRepository.findAll();
	}	
}
