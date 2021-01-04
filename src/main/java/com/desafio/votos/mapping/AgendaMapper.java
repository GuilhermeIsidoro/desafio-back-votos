package com.desafio.votos.mapping;

import org.mapstruct.Mapper;

import com.desafio.votos.model.Agenda;
import com.desafio.votos.model.dto.AgendaDTO;

@Mapper(componentModel = "spring")
public interface AgendaMapper {

	AgendaDTO toDTO(Agenda agenda);
	
	Agenda toEntity(AgendaDTO agendaDTO);
	
}
