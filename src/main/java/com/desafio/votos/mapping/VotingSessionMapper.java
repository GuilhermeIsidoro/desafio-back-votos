package com.desafio.votos.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.desafio.votos.model.VotingSession;
import com.desafio.votos.model.dto.VotingSessionDTO;

@Mapper(componentModel = "spring", uses = {AgendaMapper.class})
public interface VotingSessionMapper {

	@Mapping(source = "votingSession.agenda.id", target = "agendaId")
	VotingSessionDTO toDTO(VotingSession votingSession);
	
	@Mapping(source = "agendaId", target = "agenda.id")
	VotingSession toEntity(VotingSessionDTO votingSession);
	
}
