package com.desafio.votos.model.dto;

import java.io.Serializable;
import java.util.List;

public class AgendaVotingResultDTO implements Serializable {

	private static final long serialVersionUID = 6003521756385188727L;
	
	private AgendaDTO agenda;
	
	private List<VoteCountDTO> votes;
	
	private VoteCountDTO winner;

	public AgendaVotingResultDTO(AgendaDTO agenda, List<VoteCountDTO> votes, VoteCountDTO winner) {
		this.agenda = agenda;
		this.votes = votes;
		this.winner = winner;
	}

	public AgendaDTO getAgenda() {
		return agenda;
	}

	public void setAgenda(AgendaDTO agenda) {
		this.agenda = agenda;
	}

	public List<VoteCountDTO> getVotes() {
		return votes;
	}

	public void setVotes(List<VoteCountDTO> votes) {
		this.votes = votes;
	}

	public VoteCountDTO getWinner() {
		return winner;
	}

	public void setWinner(VoteCountDTO winner) {
		this.winner = winner;
	}

}
