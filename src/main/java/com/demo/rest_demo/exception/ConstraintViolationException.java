package com.demo.rest_demo.exception;

import java.io.Serial;

public class ConstraintViolationException extends RuntimeException {
	//validation failures in URI parameters
	@Serial
	private static final long serialVersionUID = 1L;
	
	public ConstraintViolationException( String message) {		
		super(message);		
	}
}