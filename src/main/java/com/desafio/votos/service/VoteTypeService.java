package com.desafio.votos.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.desafio.votos.exception.NotFoundException;
import com.desafio.votos.mapping.VoteTypeMapper;
import com.desafio.votos.model.VoteType;
import com.desafio.votos.model.dto.AgendaDTO;
import com.desafio.votos.model.dto.VoteTypeDTO;
import com.desafio.votos.repository.VoteTypeRepository;

@Service
public class VoteTypeService {
	
	Logger logger = LoggerFactory.getLogger(VoteTypeService.class);
	
	private final VoteTypeRepository voteTypeRepository;
	
	private final VoteTypeMapper voteTypeMapper;
	
	private final AgendaService agendaService;
	
	public VoteTypeService(VoteTypeRepository voteTypeRepository,
						   VoteTypeMapper voteTypeMapper,
						   AgendaService agendaService) {
		this.voteTypeMapper = voteTypeMapper;
		this.voteTypeRepository = voteTypeRepository;
		this.agendaService = agendaService;
	}
	
	public Optional<VoteTypeDTO> findVoteType(Long voteTypeId) {
		logger.debug("SEARCHING FOR VOTE: {}", voteTypeId);
		return voteTypeRepository.findById(voteTypeId).map(voteTypeMapper::toDTO);
	}

	public List<VoteTypeDTO> findVoteTypeByAgenda(Long agendaId) {
		logger.debug("SEARCHING VOTE TYPES FOR AGENDA: {}", agendaId);
		return voteTypeRepository.findAllByAgendaIdOrderByVoteDescription(agendaId).stream().map(voteTypeMapper::toDTO).collect(Collectors.toList());
	}

	public VoteTypeDTO saveVoteType(@Valid VoteTypeDTO voteTypeDTO) throws NotFoundException {
		
		logger.debug("CREATING NEW VOTE TYPE {}", voteTypeDTO);
		
		Optional<AgendaDTO> agenda = agendaService.find(voteTypeDTO.getAgendaId());
		
		if(!agenda.isPresent()) {
			throw new NotFoundException("A Pauta informada não existe");
		}
		
		VoteType voteType = voteTypeMapper.toEntity(voteTypeDTO);
		
		VoteType persistedVoteType = voteTypeRepository.save(voteType);
		
		return voteTypeMapper.toDTO(persistedVoteType);
	}

}
