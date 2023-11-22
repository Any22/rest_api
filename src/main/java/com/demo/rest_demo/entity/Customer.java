package com.demo.rest_demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/***************************************************************************************************
 * An entity class is the code representation of data table 
 * Each instance corresponds each row of data table 
 * each instance variable represents to each column of table
 * persistence context
 * 
 * 
 * DROP TABLE IF EXISTS customers;
CREATE TABLE customers(
	customer_id INT PRIMARY KEY ,
	customer_name VARCHAR(40) NOT NULL,
    email_address VARCHAR(255)NOT NULL
);
DROP SEQUENCE IF EXISTS seq_customer_id;
CREATE SEQUENCE seq_customer_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 100;
 SELECT * FROM customers;
 ***************************************************************************************************/

@Entity
@Table(name="customers")
public class Customer {
	
	@Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
	@Column(name = "customer_id", nullable = false)
	private Integer customerId;
	
	@Column(name = "customer_name", nullable = false)
	private String customerName;
		
	@Column(name = "email_address", nullable = false)
	private String email;
	
  
	public Customer() {
		
	}


	public Customer(Integer customerId, String customerName, String email) {
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
