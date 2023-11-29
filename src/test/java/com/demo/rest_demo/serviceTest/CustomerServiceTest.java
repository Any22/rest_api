package com.demo.rest_demo.serviceTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.rest_demo.dto.CustomerDTO;
import com.demo.rest_demo.entity.Customer;


@SpringBootTest
public class CustomerServiceTest {
	@InjectMocks
	private ModelMapper mapper;

	@BeforeEach
	public void setup() {
		this.mapper = new ModelMapper();
	}
	
	@Test 
	public void whenMapCustomerWithExactMatch_thenConvertsToDTO() {
       
		Customer customer =  new Customer(100, "Sophia","sophie@abcnetwork.com");
		CustomerDTO customerDTO = this.mapper.map(customer, CustomerDTO.class);
		
		assertEquals(customer.getCustomerName(),customerDTO.getCustomerName());
		assertEquals(customer.getCustomerId(),customerDTO.getCustomerId());
		
		
	}

}
