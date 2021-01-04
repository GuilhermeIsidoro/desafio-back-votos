package com.desafio.votos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends Exception {

	private static final long serialVersionUID = 9028206998305279515L;
	
	public InternalServerErrorException(String message) {
		super(message);
	}

}
