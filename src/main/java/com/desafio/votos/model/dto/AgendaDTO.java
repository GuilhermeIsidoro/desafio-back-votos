package com.desafio.votos.model.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AgendaDTO implements Serializable {

	private static final long serialVersionUID = 6867478035800781776L;
	
	private Long id;
	
	@NotEmpty
	@Length(max = 100)
	private String agendaName;
	
	@NotEmpty
	@Length(max = 255)
	private String agendaDescription;
	
	private List<VotingSessionDTO> votingSessions;
	
	private List<VoteTypeDTO> voteTypes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAgendaName() {
		return agendaName;
	}

	public void setAgendaName(String agendaName) {
		this.agendaName = agendaName;
	}

	public String getAgendaDescription() {
		return agendaDescription;
	}

	public void setAgendaDescription(String agendaDescription) {
		this.agendaDescription = agendaDescription;
	}

	public List<VotingSessionDTO> getVotingSessions() {
		return votingSessions;
	}

	public void setVotingSessions(List<VotingSessionDTO> votingSessions) {
		this.votingSessions = votingSessions;
	}

	public List<VoteTypeDTO> getVoteTypes() {
		return voteTypes;
	}

	public void setVoteTypes(List<VoteTypeDTO> voteTypes) {
		this.voteTypes = voteTypes;
	}
	
	public String toJson() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(this);
	}

}
