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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.desafio.votos.exception.NotFoundException;
import com.desafio.votos.model.dto.VoteTypeDTO;
import com.desafio.votos.service.VoteTypeService;

@RestController
@RequestMapping("/api/v1/vote-type")
public class VoteTypeController {
	
	private final VoteTypeService voteTypeService;

	public VoteTypeController(VoteTypeService voteTypeService) {
		this.voteTypeService = voteTypeService;
	}
	
	@GetMapping("/{voteTypeId}")
	public VoteTypeDTO findVoteType(@PathVariable("voteTypeId") Long voteTypeId) {
		return voteTypeService.findVoteType(voteTypeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de voto não encontrado"));
	}
	
	@GetMapping("/agenda/{agendaId}")
	public ResponseEntity<List<VoteTypeDTO>> findVoteTypeByAgenda(@PathVariable("agendaId") Long agendaId) {
		return ResponseEntity.ok(voteTypeService.findVoteTypeByAgenda(agendaId));
	}
	
	@PostMapping
	public ResponseEntity<VoteTypeDTO> createVoteType(@Valid @RequestBody VoteTypeDTO voteType) throws URISyntaxException, NotFoundException {
		if(voteType.getId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID não deve ser informado na criação do Tipo de voto");
		}
		VoteTypeDTO createdVoteType = voteTypeService.saveVoteType(voteType);
		return ResponseEntity.created(new URI("/api/v1/vote-type/" + createdVoteType.getId())).body(createdVoteType);
	}
	
	@PutMapping
	public ResponseEntity<VoteTypeDTO> updateVoteType(@Valid @RequestBody VoteTypeDTO voteType) throws NotFoundException {
		if(voteType.getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID não informado");
		}
		VoteTypeDTO createdVoteType = voteTypeService.saveVoteType(voteType);
		return ResponseEntity.ok(createdVoteType);
	}

}
