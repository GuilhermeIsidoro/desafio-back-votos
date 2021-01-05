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

import com.desafio.votos.exception.ConflictException;
import com.desafio.votos.exception.InternalServerErrorException;
import com.desafio.votos.exception.NotFoundException;
import com.desafio.votos.model.dto.AssociateDTO;
import com.desafio.votos.service.AssociateService;

@RestController
@RequestMapping("/api/v1/associate")
public class AssociateController {
	
	private final AssociateService associateService;
	
	public AssociateController(AssociateService associateService) {
		this.associateService = associateService;
	}

	@GetMapping
	public List<AssociateDTO> getAssociates() {
		return associateService.getAssociates();
	}
	
	@GetMapping("/{cpf}")
	public AssociateDTO findAssociate(@PathVariable("cpf") String cpf) {
		return associateService.findAssociate(cpf).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Associado não encontrado"));
	}
	
	@PostMapping
	public ResponseEntity<AssociateDTO> createAssociate(@Valid @RequestBody AssociateDTO associate) throws URISyntaxException, NotFoundException, InternalServerErrorException, ConflictException {
		AssociateDTO createdAssociate = associateService.createAssociate(associate);
		return ResponseEntity.created(new URI("/api/v1/associate/" + associate.getCpf())).body(createdAssociate);
	}
	
	@PutMapping
	public ResponseEntity<AssociateDTO> updateAssociate(@Valid @RequestBody AssociateDTO associate) throws URISyntaxException, NotFoundException, InternalServerErrorException {
		AssociateDTO createdAssociate = associateService.updateAssociate(associate);
		return ResponseEntity.ok(createdAssociate);
	}
	
}
