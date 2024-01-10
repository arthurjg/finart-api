package br.com.artsoft.finart.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.artsoft.finart.controller.dto.InvestimentoDTO;
import br.com.artsoft.finart.model.domain.investimento.Investimento;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	ModelMapper modelMapper() {
		
		var modelMapper = new ModelMapper();
		
		modelMapper.createTypeMap(Investimento.class, InvestimentoDTO.class)
			.<String>addMapping(src -> src.getTipo().getNome(), (dest, val) -> dest.setTipo(val))
			.<String>addMapping(src -> src.getTipo().getClasse().getNome(), (dest, val) -> dest.setNatureza(val));
		
		
		return modelMapper;		
	}

}
