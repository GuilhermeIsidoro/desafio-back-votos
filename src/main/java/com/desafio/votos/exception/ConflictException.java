package com.desafio.votos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ConflictException extends Exception {

	private static final long serialVersionUID = 9028206998305279515L;
	
	public ConflictException(String message) {
		super(message);
	}

}
