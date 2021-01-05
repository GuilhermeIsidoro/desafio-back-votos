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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.desafio.votos.exception.ForbiddenException;
import com.desafio.votos.exception.InternalServerErrorException;
import com.desafio.votos.exception.NotFoundException;
import com.desafio.votos.model.dto.AgendaVotingResultDTO;
import com.desafio.votos.model.dto.VoteDTO;
import com.desafio.votos.model.dto.VotingDTO;
import com.desafio.votos.service.VoteService;

@RestController
@RequestMapping("/api/v1/vote")
public class VoteController {
	
	private final VoteService voteService;
	
	public VoteController(VoteService voteService) {
		this.voteService = voteService;
	}

	@GetMapping("/agenda/{agendaId}")
	public ResponseEntity<List<VoteDTO>> findVotes(@PathVariable("agendaId") Long agendaId) {
		return ResponseEntity.ok(voteService.findVotesByAgenda(agendaId));
	}
	
	@GetMapping("/{voteId}")
	public VoteDTO findVoteById(@PathVariable("voteId") Long voteId) {
		return voteService.findVoteById(voteId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Voto não encontrado"));
	}
	
	@PostMapping
	public ResponseEntity<VoteDTO> vote(@Valid @RequestBody VotingDTO voting) throws URISyntaxException, NotFoundException, InternalServerErrorException, ForbiddenException {
		VoteDTO vote = voteService.vote(voting);
		return ResponseEntity.created(new URI("/api/v1/vote/" + vote.getId())).body(vote);
	}
	
	@GetMapping("/result/agenda/{agendaId}")
	public ResponseEntity<AgendaVotingResultDTO> agendaVotingResult(@PathVariable("agendaId") Long agendaId) throws NotFoundException {
		return ResponseEntity.ok(voteService.checkResult(agendaId));
	}

}
