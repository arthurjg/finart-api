package br.com.artsoft.finart.model.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.artsoft.finart.model.domain.Conta;
import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.repository.ContaRepository;

@Service
public class ContaRN {
	
	@Autowired
	private ContaRepository contaRepository;	

	public List<Conta> listar(Usuario usuario) {	
		List<Conta> lista = contaRepository.findAllByUsuario(usuario);
		return somarSaldoLista(lista);
	}

	public Conta carregar(Integer conta) {
		return contaRepository.findById(conta).get();
	}

	public void salvar(Conta conta) {
		conta.setDataCadastro(new Date());
		contaRepository.save(conta);
	}

	public void excluir(Conta conta) {
		Optional<Conta> contaPersistida = contaRepository.findById(conta.getConta());
		if(contaPersistida.isPresent()){
			contaRepository.delete(conta);
		}		
	}

	public void tornarFavorita(Conta contaFavorita) { 
		Conta conta = buscarFavorita(contaFavorita.getUsuario());
		if (conta != null) {
			conta.setFavorita(false);
			contaRepository.save(conta);
		}

		contaFavorita.setFavorita(true);
		contaRepository.save(contaFavorita);
	}

	public Conta buscarFavorita(Usuario usuario) {
		return contaRepository.findByUsuarioAndFavorita(usuario, true);
	}
	
	public List<Conta> somarSaldoLista(List<Conta> lista) {
		Conta contaTotal = new Conta();
		contaTotal.setDescricao("Total - ");
		long total = 0;
		for( Conta conta : lista){
			total += conta.getSaldoAtual();
		}
		contaTotal.setSaldoAtual(total);
		lista.add(contaTotal);
		return lista;
	}
}
