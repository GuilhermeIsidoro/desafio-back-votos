package com.desafio.votos.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.desafio.votos.exception.NotFoundException;
import com.desafio.votos.exception.UnprocessableEntityException;
import com.desafio.votos.mapping.VotingSessionMapper;
import com.desafio.votos.model.VotingSession;
import com.desafio.votos.model.dto.AgendaDTO;
import com.desafio.votos.model.dto.VotingSessionDTO;
import com.desafio.votos.repository.VotingSessionRepository;

@Service
public class VotingSessionService {
	
	Logger logger = LoggerFactory.getLogger(VotingSessionService.class);
	
	private final VotingSessionRepository votingSessionRepository;
	
	private final VotingSessionMapper votingSessionMapper;
	
	private final AgendaService agendaService;
	
	public VotingSessionService(VotingSessionRepository votingSessionRepository,
								VotingSessionMapper votingSessionMapper,
								AgendaService agendaService) {
		this.votingSessionRepository = votingSessionRepository;
		this.votingSessionMapper = votingSessionMapper;
		this.agendaService = agendaService;
		
	}
	
	public Optional<VotingSessionDTO> find(Long id) {
		logger.debug("SEARCHING FOR VOTING SESSION: {}", id);
		return votingSessionRepository.findById(id).map(votingSessionMapper::toDTO);
	}

	public List<VotingSessionDTO> findAll(boolean openOnly) {
		if(openOnly) {
			logger.debug("SEARCHING FOR ALL VOTING SESSIONS");
			LocalDateTime now = LocalDateTime.now();
			return votingSessionRepository.findOpenSessions(now).stream().map(votingSessionMapper::toDTO).collect(Collectors.toList());
		} else {
			logger.debug("SEARCHING FOR OPEN VOTING SESSIONS");
			return votingSessionRepository.findAll().stream().map(votingSessionMapper::toDTO).collect(Collectors.toList());			
		}
	}

	public VotingSessionDTO saveSession(@Valid VotingSessionDTO votingSessionDTO) throws UnprocessableEntityException, NotFoundException {
		
		logger.debug("SAVING VOTING SESSION: {}", votingSessionDTO);
		
		if(votingSessionDTO.getEndTime() == null) {
			votingSessionDTO.setEndTime(votingSessionDTO.getStartTime().plusMinutes(1));
		}
		
		if(votingSessionDTO.getEndTime().isBefore(votingSessionDTO.getStartTime())) {
			throw new UnprocessableEntityException("O horário Fim da sessão deve ser maior que o horário de início");
		}
		
		Optional<AgendaDTO> agenda = agendaService.find(votingSessionDTO.getAgendaId());
		
		if(!agenda.isPresent()) {
			throw new NotFoundException("Agenda informada não existe");
		}
		
		VotingSession votingSession = votingSessionMapper.toEntity(votingSessionDTO);
		
		VotingSession createdVotingSession = votingSessionRepository.save(votingSession);
		
		return votingSessionMapper.toDTO(createdVotingSession);
	}

}
