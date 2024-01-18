package br.com.artsoft.finart.model.service.investimento;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.artsoft.finart.model.domain.Usuario;
import br.com.artsoft.finart.model.domain.investimento.Investimento;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoTipo;
import br.com.artsoft.finart.model.repository.InvestimentoRepository;
import br.com.artsoft.finart.model.repository.InvestimentoTipoRepository;

@ExtendWith(MockitoExtension.class)
class InvestimentoServiceTest {
	
	@InjectMocks
	InvestimentoService service;
	
	@Mock
	InvestimentoRepository	investimentoRepository;	
	
	@Mock
	InvestimentoTipoRepository investimentoTipoRepository;	

	@Test
	void testSalvar() {
		
		Usuario usuario = Usuario.builder()
				.nome("John Nobody")
				.build();		
		
		String tipo = "CDB";
		
		InvestimentoTipo investimentoTipo = InvestimentoTipo.builder()
				.nome(tipo)
				.tipo(tipo)
				.build();
		
		Investimento investimento = Investimento.builder()
				.nome("IVVB11")				
				.usuario(usuario)
				.build();
		
		when(investimentoTipoRepository.findByTipo(tipo)).thenReturn(Optional.of(investimentoTipo));
		
		service.salvar(investimento, tipo);
		
		verify(investimentoRepository).save(investimento);
	}

}
