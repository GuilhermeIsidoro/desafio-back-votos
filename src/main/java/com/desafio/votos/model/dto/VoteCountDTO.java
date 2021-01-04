package com.desafio.votos.model.dto;

import java.io.Serializable;

public class VoteCountDTO implements Serializable {

	private static final long serialVersionUID = -9171145021031715523L;
	
	private String voteDescription;
	
	private Long voteCount;

	public String getVoteDescription() {
		return voteDescription;
	}

	public void setVoteDescription(String voteDescription) {
		this.voteDescription = voteDescription;
	}

	public Long getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(Long voteCount) {
		this.voteCount = voteCount;
	}

}
