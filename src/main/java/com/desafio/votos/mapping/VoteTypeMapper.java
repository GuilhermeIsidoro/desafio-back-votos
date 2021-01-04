package com.desafio.votos.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.desafio.votos.model.VoteType;
import com.desafio.votos.model.dto.VoteTypeDTO;

@Mapper(componentModel = "spring", uses = {VoteMapper.class, AgendaMapper.class})
public interface VoteTypeMapper {
	
	@Mapping(target = "agendaId", source = "agenda.id")
	VoteTypeDTO toDTO(VoteType voteType);
	
	@Mapping(target = "agenda.id", source = "agendaId")
	VoteType toEntity(VoteTypeDTO voteTypeDTO);

}
