package com.demo.rest_demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
/********************************************************************************************************************************************
 * Java EE (up to version 8): If you are working with a Java EE version up to 8 or any framework that uses the Java EE validation API,
 *  you should use javax.validation.constraints.
 *  Jakarta EE (version 9 onwards): If you are using Jakarta EE version 9 or later, you should use jakarta.validation.constraints.
 *  The org.hibernate.validator.constraints package contains additional constraints provided by the Hibernate Validator framework, which is 
 *  an implementation of the Java Bean Validation API. These constraints are Hibernate-specific and may not be part of the standard Bean 
 *  Validation API.
 *  for example : 
 *  @CreditCardNumber: Validates that a given string is a valid credit card number.
	@Currency: Validates that a given string represents a valid currency.
	@Email: Validates that a given string is a valid email address.
	@Length: Validates that the length of the annotated element is between min and max (inclusive).
	@Range: Checks whether the annotated value lies between (inclusive) the specified minimum and maximum.
 *******************************************************************************************************************************************/



/*******************************************************************************************************************************************
 * Data transfer object that carries data from one layer to another for e.g the form data reaches the controller layer using DTO objects 
 * 
 ******************************************************************************************************************************************/

public class CustomerDTO {
	@NotNull
	private Integer customerId;
	@NotEmpty
	private String customerName;
	@NotNull
	@Email
	private String email;
	
	
	public CustomerDTO() {
	
	}


	public CustomerDTO(Integer customerId, String customerName, String email) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.email = email;
	}


	public Integer getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
