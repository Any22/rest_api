package com.demo.rest_demo.exception;

public class ConstraintViolationException extends Exception  {
	//validation failures in URI parameters
	private static final long serialVersionUID = 1L;
	
	public ConstraintViolationException( String message) {		
		super(message);		
	}
}