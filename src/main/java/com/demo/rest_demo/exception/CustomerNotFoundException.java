package com.demo.rest_demo.exception;

import java.io.Serial;

public class CustomerNotFoundException extends RuntimeException  {
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	public CustomerNotFoundException() {		
		super();		
	}
	public CustomerNotFoundException( String message) {		
		super(message);		
	}
	

}
