package com.desafio.votos.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.desafio.votos.model.Associate;
import com.desafio.votos.model.dto.AssociateDTO;


@Mapper(componentModel = "spring")
public interface AssociateMapper {

	@Mapping(target = "cpf", source = "associate", qualifiedByName = "maskCPF")
	AssociateDTO toDTO(Associate associate);
	
	@Mapping(target = "id", source = "cpf")
	Associate toEntity(AssociateDTO associateDTO);
	
	@Named("maskCPF")
	default String maskCPF(Associate associate) {
		return associate.maskCPF();
	}
	
}
