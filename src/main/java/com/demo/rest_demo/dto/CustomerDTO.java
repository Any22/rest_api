package com.demo.rest_demo.dto;

/************************************************************************
 * Data transfer object that carries data from one layer to another
 * for e.g the form data reaches the controller layer using DTO objects 
 * 
 ************************************************************************/

public class CustomerDTO {
	private Integer customerId;
	private String customerName;
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
