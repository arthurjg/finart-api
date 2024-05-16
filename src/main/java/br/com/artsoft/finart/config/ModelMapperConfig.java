package br.com.artsoft.finart.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.artsoft.finart.controller.investimento.dto.InvestimentoDetalhesDTO;
import br.com.artsoft.finart.model.domain.investimento.InvestimentoSumario;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	ModelMapper modelMapper() {
		
		var modelMapper = new ModelMapper();
		
		modelMapper.createTypeMap(InvestimentoSumario.class, InvestimentoDetalhesDTO.class)
			.<String>addMapping(src -> src.getTipo().getNome(), (dest, val) -> dest.setTipo(val))
			.<String>addMapping(src -> src.getTipo().getClasse().getNome(), (dest, val) -> dest.setNatureza(val));
		
		/*modelMapper.typeMap(InvestimentoMovimentoDetalhesDTO.class, InvestimentoMovimento.class).addMappings(
				mapper -> {
			  mapper.<LocalDateTime>map(src -> LocalDateTime.parse(src.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm:ss")),
			      (dest, val) -> dest.setData(val));
			  
			});*/
		
		
		return modelMapper;		
	}

}
