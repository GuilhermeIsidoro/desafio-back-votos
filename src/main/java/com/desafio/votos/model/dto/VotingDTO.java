package com.desafio.votos.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VotingDTO implements Serializable {

	private static final long serialVersionUID = -6562410487457292759L;
	
	@NotEmpty
	@Length(min = 11, max = 11)
	private String associateCPF;
	
	@NotNull
	private Long voteTypeId;
	
	@NotNull
	private Long agendaId;
	
	@NotNull
	private Long votingSessionId;

	public String getAssociateCPF() {
		return associateCPF;
	}

	public void setAssociateCPF(String associateCPF) {
		this.associateCPF = associateCPF;
	}

	public Long getVoteTypeId() {
		return voteTypeId;
	}

	public void setVoteTypeId(Long voteTypeId) {
		this.voteTypeId = voteTypeId;
	}

	public Long getAgendaId() {
		return agendaId;
	}

	public void setAgendaId(Long agendaId) {
		this.agendaId = agendaId;
	}

	public Long getVotingSessionId() {
		return votingSessionId;
	}

	public void setVotingSessionId(Long votingSessionId) {
		this.votingSessionId = votingSessionId;
	}
	
	public String toJson() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(this);
	}

}
