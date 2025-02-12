package com.demo.rest_demo.exception;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.demo.rest_demo.util.RestDemoConstant;

import jakarta.validation.ConstraintViolationException;
/************************************************************************************************************************************
 * a real world application can handle the exceptions and validation failures 
 * @author saba akhtar
 * Generalized handler 
 * Mentioning the type in @ExceptionHandler is mandatory when there are multiple types of exceptions to be handled by a single
 * single handler. 
 * validations can be done in request body or uri parameters 
 * we are handling validations in a centralized way and customized messages are generated 
 ***********************************************************************************************************************************/

@RestControllerAdvice
public class GlobalExceptionHandler {
	@Autowired 
	private Environment environment;
	
	@ExceptionHandler (Exception.class)
	public ResponseEntity<ErrorMessage> generalExceptionHandler(Exception ex){
		ErrorMessage error = new ErrorMessage();
		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler (CustomerNotFoundException.class)
	public ResponseEntity<ErrorMessage> customerNotFoundExceptionHandler(CustomerNotFoundException ex){
		ErrorMessage error = new ErrorMessage();
		error.setErrorCode(HttpStatus.NOT_FOUND.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler (NoDataFoundException.class)
	public ResponseEntity<ErrorMessage> noDataFoundExceptionHandler(NoDataFoundException ex){
		ErrorMessage error = new ErrorMessage();
		error.setErrorCode(HttpStatus.NOT_FOUND.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}

	//Handler for validation failure w.r.t DTOs or validation failures request body 
	@ExceptionHandler (MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> methodArgumentExceptionHandler (MethodArgumentNotValidException ex){
		ErrorMessage error = new ErrorMessage();
		error.setErrorCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
				.collect(Collectors.joining(", ")));
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
	
	//Handler for validation failure w.r.t URI parameters 
	@ExceptionHandler (ConstraintViolationException.class)
	public ResponseEntity<ErrorMessage> constraintViolationExceptionHandler ( ConstraintViolationException ex){
		ErrorMessage error = new ErrorMessage();
		error.setErrorCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage("check the parameters and data object");
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler (NoResourceFoundException.class)
	public ResponseEntity<ErrorMessage> noResourceFoundExceptionHandler ( NoResourceFoundException ex){
		ErrorMessage error = new ErrorMessage();
		error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setMessage("no resourcse found.Please check uri is valid or not");
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
}
