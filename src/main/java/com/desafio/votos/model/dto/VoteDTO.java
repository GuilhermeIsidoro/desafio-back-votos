package com.desafio.votos.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class VoteDTO implements Serializable {

	private static final long serialVersionUID = 8080072035184883297L;
	
	private Long id;
	
	private LocalDateTime votingDate;
	
	@NotNull
	private VoteTypeDTO voteType;
	
	private AgendaDTO agenda;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getVotingDate() {
		return votingDate;
	}

	public void setVotingDate(LocalDateTime votingDate) {
		this.votingDate = votingDate;
	}

	public VoteTypeDTO getVoteType() {
		return voteType;
	}

	public void setVoteType(VoteTypeDTO voteType) {
		this.voteType = voteType;
	}

	public AgendaDTO getAgenda() {
		return agenda;
	}

	public void setAgenda(AgendaDTO agenda) {
		this.agenda = agenda;
	}

}
