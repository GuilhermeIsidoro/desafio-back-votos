package com.desafio.votos.mapping;

import org.mapstruct.Mapper;

import com.desafio.votos.model.dto.VoteCountDTO;
import com.desafio.votos.projection.VoteCountProjection;

@Mapper(componentModel = "spring")
public interface VoteCountMapper {

	VoteCountDTO toDTO(VoteCountProjection voteCount);
	
}
