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

import com.desafio.votos.exception.NotFoundException;
import com.desafio.votos.exception.UnprocessableEntityException;
import com.desafio.votos.model.dto.VotingSessionDTO;
import com.desafio.votos.service.VotingSessionService;

@RestController
@RequestMapping("api/v1/voting-session")
public class VotingSessionController {
	
	private final VotingSessionService votingSessionService;
	
	public VotingSessionController(VotingSessionService votingSessionService) {
		this.votingSessionService = votingSessionService;		
	}
	
	@GetMapping
	public ResponseEntity<List<VotingSessionDTO>> findSessions(@RequestParam(name = "open_only", required = false) boolean openOnly) {
		return ResponseEntity.ok(votingSessionService.findAll(openOnly));
	}
	
	@GetMapping("/{votingSessionId}")
    public VotingSessionDTO findSessionById(@PathVariable("votingSessionId") Long id) {
		return votingSessionService.find(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sessão não encontrada"));
    }
	
	@PostMapping
	public ResponseEntity<VotingSessionDTO> createVotingSession(@Valid @RequestBody VotingSessionDTO votingSession) throws URISyntaxException, UnprocessableEntityException, NotFoundException {
		if(votingSession.getId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID não deve ser informado na criação da Sessão");
		}
		
		VotingSessionDTO createdVotingSession = votingSessionService.saveSession(votingSession);
		
		return ResponseEntity.created(new URI("api/v1/voting-session/" + createdVotingSession.getId())).body(createdVotingSession);
	}
	
	@PutMapping
	public ResponseEntity<VotingSessionDTO> updateVotingSession(@Valid @RequestBody VotingSessionDTO votingSession) throws UnprocessableEntityException, NotFoundException {
		if(votingSession.getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID não informado");
		}
		
		VotingSessionDTO createdVotingSession = votingSessionService.saveSession(votingSession);
		
		return ResponseEntity.ok(createdVotingSession);
	}

}
