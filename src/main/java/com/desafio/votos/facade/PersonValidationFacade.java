package com.desafio.votos.facade;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.desafio.votos.exception.InternalServerErrorException;
import com.desafio.votos.exception.NotFoundException;
import com.desafio.votos.model.dto.PersonStatusDTO;

@Service
public class PersonValidationFacade {
	
	Logger logger = LoggerFactory.getLogger(PersonValidationFacade.class);

	@Value("${person.validation.host}")
	private String personValidationHost;
	
	@Value("${person.validation.path}")
	private String personValidationPath;
	
	private final RestTemplate restTemplate;
	
	public PersonValidationFacade(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public PersonStatusDTO validatePerson(String cpf) throws URISyntaxException, NotFoundException, InternalServerErrorException {
		
		try {
			URI uri = new URI(personValidationHost + personValidationPath + cpf);
			
			return restTemplate.getForObject(uri, PersonStatusDTO.class);			
		} catch(HttpClientErrorException ex) {
			if(ex.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new NotFoundException("Pessoa não encontrada");
			}
			
			throw new InternalServerErrorException(ex.getMessage());
		}
	}
	
}
