package com.desafio.votos.model.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.desafio.votos.model.dto.AgendaDTO;
import com.desafio.votos.service.AgendaService;

@RestController
@RequestMapping("/api/v1/agenda")
public class AgendaController {
	
	private final AgendaService agendaService;
	
	public AgendaController(AgendaService agendaService) {
		this.agendaService = agendaService;
	}
	
	@GetMapping
	public ResponseEntity<List<AgendaDTO>> getAgendas(@RequestParam(name = "open_sessions_only", required = false) boolean openSessionsOnly) {
		return ResponseEntity.ok(agendaService.findAgendas(openSessionsOnly));
	}

	@GetMapping("/{id}")
    public AgendaDTO getAgenda(@PathVariable("id") Long id) {
		return agendaService.find(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pauta não encontrada"));
    }
	
	@PostMapping
	public ResponseEntity<AgendaDTO> createAgenda(@Valid @RequestBody AgendaDTO newAgenda) throws URISyntaxException {
		if(newAgenda.getId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID não deve ser informado na criação da Pauta");
		}
		
		AgendaDTO createdAgenda = agendaService.saveAgenda(newAgenda);
		
		return ResponseEntity.created(new URI("/api/v1/agenda/" + createdAgenda.getId())).body(createdAgenda);
	}
	
	@PutMapping
	public ResponseEntity<AgendaDTO> updateAgenda(@Valid @RequestBody AgendaDTO agenda) {
		if(agenda.getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID não informado");
		}
		
		// Validates if informed Agenda exists
		getAgenda(agenda.getId());
		
		AgendaDTO updatedAgenda = agendaService.saveAgenda(agenda);
		
		return ResponseEntity.ok(updatedAgenda);
	}
	
}
