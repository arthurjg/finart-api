
package br.com.artsoft.finart.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.domain.investimento.Investimento;
import br.com.artsoft.finart.model.repository.InvestimentoRepository;

@Service
public class InvestimentoRN {

	@Autowired
	private InvestimentoRepository	investimentoRepository;		

	public void salvar(Investimento investimento) {
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
