package com.demo.rest_demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.rest_demo.dto.CustomerDTO;
import com.demo.rest_demo.entity.Customer;
import com.demo.rest_demo.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
    private ModelMapper modelMapper;  
	@Autowired
	private CustomerRepository customerRepository;

	
	public List<CustomerDTO> getAllCustomer() {
		
		// getting all entities from DB 
	    List<Customer> customer = customerRepository.findAll();
	   
	    //creating a list to convert and store all entities into DTOs
	    
	    List<CustomerDTO> customerDTOs = new ArrayList<>();
	    
	    return customer.stream()
	            .map(this::convertToDto)
	            .collect(Collectors.toList());
	    
	}
	
	/*************************************************************************************************************************
	 * creating a customer 
	 * Object Mapper converts DTO to Entity and back from Entity to DTO.
	 * 
	 * @param email
	 * 
	 * @param name
	 * 
	 * @return a message of successful creation
	 * 
	 **************************************************************************************************************************/
	
	 public Customer saveCustomer(CustomerDTO customerDTO) {
                   
	       Customer customerEntity = new Customer();
	    
	       customerEntity.setCustomerName(customerDTO.getCustomerName());
	       customerEntity.setEmail(customerDTO.getEmail());
	      
	       customerRepository.save(customerEntity);
	
	       return customerEntity;
	   
	    }

	 
	 
    // converting into DTO
	 
    public CustomerDTO convertToDto(Customer customer) {
    
        return modelMapper.map(customer, CustomerDTO.class);
    }


	
	
}
		
	

