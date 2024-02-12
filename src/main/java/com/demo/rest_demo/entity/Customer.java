package com.demo.rest_demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***************************************************************************************************
 * An entity class is the code representation of data table 
 * Each instance corresponds each row of data table 
 * each instance variable represents to each column of table
 * persistence context
 * 
 ***************************************************************************************************/

@Entity
@Table(name="customers")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_customer_id")
	@SequenceGenerator(name = "seq_customer_id", sequenceName = "seq_customer_id", allocationSize = 1)
	private Integer customerId;
	
	@Column(name = "customer_name", nullable = false)
	private String customerName;
		
	@Column(name = "email_address", nullable = false)
	private String email;
		
}
