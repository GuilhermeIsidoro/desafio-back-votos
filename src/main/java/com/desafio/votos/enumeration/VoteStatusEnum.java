package com.desafio.votos.enumeration;

public enum VoteStatusEnum {
	
	ABLE_TO_VOTE("ABLE_TO_VOTE"),
	UNABLE_TO_VOTE("UNABLE_TO_VOTE");
	
	private String status;
	
	private VoteStatusEnum(String status) {
		this.status = status;
	}
	
	public String status() {
		return this.status;
	}
	
}
