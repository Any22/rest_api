package com.demo.rest_demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
	
	@NotNull 
	@Min(value = 1, message = "Customer ID must be a positive integer")
	private Integer customerId;
	
	@NotNull (message="{customer.name.validation}")
	private String customerName;
	
	
	@Email (message ="{customer.email.validation}")
	private String email;
	
}
