package com.desafio.votos.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.desafio.votos.model.Vote;
import com.desafio.votos.model.dto.VoteDTO;
import com.desafio.votos.model.dto.VotingDTO;

@Mapper(componentModel = "spring", uses = {VoteTypeMapper.class, AgendaMapper.class, VoteTypeMapper.class})
public interface VoteMapper {
	
	VoteDTO toDTO(Vote vote);
	
	Vote toEntity(VoteDTO voteDTO);
	
	@Mapping(target = "voteType.id", source = "voteTypeId")
	@Mapping(target = "associate.id", source = "associateCPF")
	@Mapping(target = "agenda.id", source = "agendaId")
	Vote toEntity(VotingDTO votingDTO);

}
