package com.desafio.votos.service;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.desafio.votos.exception.ConflictException;
import com.desafio.votos.exception.InternalServerErrorException;
import com.desafio.votos.exception.NotFoundException;
import com.desafio.votos.facade.PersonValidationFacade;
import com.desafio.votos.mapping.AssociateMapper;
import com.desafio.votos.model.Associate;
import com.desafio.votos.model.dto.AssociateDTO;
import com.desafio.votos.repository.AssociateRepository;

@Service
public class AssociateService {
	
	Logger logger = LoggerFactory.getLogger(AssociateService.class);

	private final AssociateMapper associateMapper;
	
	private final AssociateRepository associateRepository;
	
	private final PersonValidationFacade personValidationFacade;

	public AssociateService(AssociateMapper associateMapper,
							AssociateRepository associateRepository,
							PersonValidationFacade personValidationFacade) {
		this.associateMapper = associateMapper;
		this.associateRepository = associateRepository;
		this.personValidationFacade = personValidationFacade;
	}
	
	public List<AssociateDTO> getAssociates() {
		logger.debug("SEARCHING FOR ALL ASSOCIATES");
		return associateRepository.findAllByOrderByNameAsc().stream().map(associateMapper::toDTO).collect(Collectors.toList());
	}

	public Optional<AssociateDTO> findAssociate(String cpf) {
		logger.debug("SEARCHING FOR ASSOCIATE");
		return associateRepository.findById(cpf).map(associateMapper::toDTO);
	}

	public AssociateDTO createAssociate(@Valid AssociateDTO newAssociate) throws URISyntaxException, NotFoundException, InternalServerErrorException, ConflictException {
		
		logger.debug("CREATING NEW ASSOCIATE");
		
		personValidationFacade.validatePerson(newAssociate.getCpf());
		
		Optional<AssociateDTO> existingAssociate = findAssociate(newAssociate.getCpf());
		
		if(existingAssociate.isPresent()) {
			throw new ConflictException("Associado já cadastrado");
		}
		
		Associate associate = associateMapper.toEntity(newAssociate);
		
		Associate createdAssociate = associateRepository.save(associate);
		
		return associateMapper.toDTO(createdAssociate);
	}
	
	public AssociateDTO updateAssociate(@Valid AssociateDTO updatedAssociate) throws URISyntaxException, NotFoundException, InternalServerErrorException {
		
		logger.debug("UPDATING ASSOCIATE");
		
		personValidationFacade.validatePerson(updatedAssociate.getCpf());
		
		Associate associate = associateMapper.toEntity(updatedAssociate);
		
		Associate createdAssociate = associateRepository.save(associate);
		
		return associateMapper.toDTO(createdAssociate);
	}
	
}
