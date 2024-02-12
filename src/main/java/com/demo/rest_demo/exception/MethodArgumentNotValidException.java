package com.demo.rest_demo.exception;

import org.springframework.validation.Errors;
//validations for failures in DTOs
public class MethodArgumentNotValidException extends Exception  {
	
	private static final long serialVersionUID = 1L;
	public MethodArgumentNotValidException() {		
		super();		
	} 
	public MethodArgumentNotValidException( Errors errors) {		
		super();		
	} 

}


