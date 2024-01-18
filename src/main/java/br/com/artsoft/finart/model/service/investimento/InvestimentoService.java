
package br.com.artsoft.finart.model.service.investimento;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.domain.investimento.Investimento;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoTipo;
import br.com.artsoft.finart.model.repository.InvestimentoRepository;
import br.com.artsoft.finart.model.repository.InvestimentoTipoRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InvestimentoService {
	
	private final InvestimentoRepository investimentoRepository;
	private final InvestimentoTipoRepository investimentoTipoRepository;

	public void salvar(Investimento investimento, String tipo) {
		
		Optional<InvestimentoTipo> tipoOptional = investimentoTipoRepository.findByTipo(tipo);
		
		InvestimentoTipo investimentoTipo = tipoOptional.orElseThrow();
		investimento.setTipo(investimentoTipo);
		
		investimentoRepository.save(investimento);
	}

	public void excluir(Investimento investimento) {
		investimentoRepository.delete(investimento);
	}

	public Investimento carregar(Integer codigo) {
		return investimentoRepository.findById(codigo).get();
	}

	public List<Investimento> listar(Usuario usuario) {
		return investimentoRepository.findByUsuario(usuario);
	}	
}
