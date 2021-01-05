package com.desafio.votos.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.desafio.votos.mapping.AgendaMapper;
import com.desafio.votos.model.Agenda;
import com.desafio.votos.model.dto.AgendaDTO;
import com.desafio.votos.repository.AgendaRepository;

@Service
public class AgendaService {
	
	Logger logger = LoggerFactory.getLogger(AgendaService.class);
	
	private final AgendaRepository agendaRepository;
	
	private final AgendaMapper agendaMapper;
	
	public AgendaService (AgendaRepository agendaRepository,
						  AgendaMapper agendaMapper) {
		this.agendaRepository = agendaRepository;
		this.agendaMapper = agendaMapper;		
	}
	
	public Optional<AgendaDTO> find(Long id) {
		logger.debug("SEARCHING FOR AGENDA: {}", id);
		return agendaRepository.findById(id).map(agendaMapper::toDTO);
	}

	public List<AgendaDTO> findAgendas(boolean openSessionsOnly) {
		if(openSessionsOnly) {
			logger.debug("SEARCHING FOR OPEN SESSIONS");
			LocalDateTime now = LocalDateTime.now();
			return agendaRepository.findAgendasWithOpenSessions(now).stream().map(agendaMapper::toDTO).collect(Collectors.toList());
		} else {
			logger.debug("SEARCHING FOR ALL SESSIONS");
			return agendaRepository.findAll().stream().map(agendaMapper::toDTO).collect(Collectors.toList());
		}
	}

	public AgendaDTO saveAgenda(@Valid AgendaDTO newAgenda) {
		
		logger.debug("SAVING AGENDA {}", newAgenda);
		
		Agenda agenda = agendaMapper.toEntity(newAgenda);
		
		Agenda createdAgenda = agendaRepository.save(agenda);
		
		return agendaMapper.toDTO(createdAgenda);
	}

}
