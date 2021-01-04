package com.desafio.votos.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VoteTypeDTO implements Serializable {

	private static final long serialVersionUID = -1497870476608535470L;
	
	private Long id;
	
	@NotEmpty
	@Length(max = 45)
	private String voteDescription;
	
	@NotNull
	private Long agendaId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVoteDescription() {
		return voteDescription;
	}

	public void setVoteDescription(String voteDescription) {
		this.voteDescription = voteDescription;
	}

	public Long getAgendaId() {
		return agendaId;
	}

	public void setAgendaId(Long agendaId) {
		this.agendaId = agendaId;
	}
	
	public String toJson() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(this);
	}

}
