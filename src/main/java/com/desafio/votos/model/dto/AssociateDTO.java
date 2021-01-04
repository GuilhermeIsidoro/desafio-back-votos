package com.desafio.votos.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AssociateDTO implements Serializable {

	private static final long serialVersionUID = 8480249109140745870L;
	
	@NotEmpty
	@Length(min = 11, max = 11)
	private String cpf;
	
	@NotEmpty
	@Length(max = 255)
	private String name;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toJson() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(this);
	}

}
