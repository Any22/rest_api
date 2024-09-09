package com.demo.rest_demo.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage {
	
	private int errorCode;
	private String message;

}
