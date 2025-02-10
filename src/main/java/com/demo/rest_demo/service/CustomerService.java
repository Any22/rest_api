package com.demo.rest_demo.service;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import com.demo.rest_demo.dto.CustomerDTO;
import com.demo.rest_demo.entity.Customer;
import com.demo.rest_demo.exception.CustomerNotFoundException;
import com.demo.rest_demo.repository.CustomerRepository;


@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Value("${customer.not.found}")
	private String idNotFound;
	
	@Value("${customer.id.null}")
	private String idNotNull;


	private static final Log LOGGER = LogFactory.getLog(CustomerService.class);

	/*************************************************************************************************************************
	 * The method is getting list of Customer DTOs which was stored in the form of entities
	 * @return list of CustomerDTO which was stored in the form of entities
	 *************************************************************************************************************************/

	public List<CustomerDTO> getAllCustomer() {

		// getting all entities from DB 
		List<Customer> customer = customerRepository.findAll();

		//creating a list of CustomerDTOs by converting entities into DTOs using "convertToDto" method

		//List<CustomerDTO> customerDTOs = new ArrayList<>();

		return customer.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());

	}
	
    public CustomerDTO getCustomerById(Integer customerId) throws CustomerNotFoundException {
		
		// getting the customer from DB 
    	
	    Optional<Customer> customerOptional = customerRepository.findById(customerId);
	   
	    if (customerOptional.isPresent()) {
	    	//very important line otherwise we will get null
	    	Customer customer = customerOptional.get();
	    	LOGGER.info("customer is present "+customer.getCustomerName());
	    	 return convertToDto(customer);
	      
	    }   else {
	    	LOGGER.info("the customerId doesnot exist"+ customerId);
	    	throw new CustomerNotFoundException(idNotFound);
	    
	    }
	}

	/*************************************************************************************************************************
	 * A helper method for Converting a customer DTO into customer entity to save into the repository
	 **************************************************************************************************************************/

	public void saveCustomer(CustomerDTO customerDTO) {

		Customer customerEntity = new Customer();

		customerEntity.setCustomerName(customerDTO.getCustomerName());
		customerEntity.setEmail(customerDTO.getEmail());

		customerRepository.saveAndFlush(customerEntity);

	}

    /**************************************************************************************************************************************
     * 
     * @param customerId and customerDto
     * @return CustomerDTO
     **************************************************************************************************************************************/
    
    
    public CustomerDTO updateCustomer(Integer customerId,CustomerDTO customerDto) throws CustomerNotFoundException {
    	Customer customer ;
    	try {
    	if (customerId == null) {
    	        throw new IllegalArgumentException(idNotNull);
    	    }
    	
    	Optional<Customer> customerInRepo =  customerRepository.findById(customerId);
    	
    	if (customerInRepo.isPresent()) {
    		customer = customerInRepo.get();
    		customer.setEmail(customerDto.getEmail());
    		customer.setCustomerName(customerDto.getCustomerName());
    		customerRepository.saveAndFlush(customer);
    	
        } 	else {
        	
        	throw new CustomerNotFoundException(idNotFound);
           }
    	
    	} catch(Exception e) {
    		e.getStackTrace();
    		LOGGER.error(e.getStackTrace());
    		throw e;
    	}
    	return  this.convertToDto(customer);
    
    }

    public void deleteCustomer(Integer customerId) throws CustomerNotFoundException {
    	Customer customer ;
    	Optional<Customer> customerInRepo =  customerRepository.findById(customerId);
    	
    	if (customerInRepo.isPresent()) {
    		customer = customerInRepo.get();
    		customerRepository.deleteById(customer.getCustomerId());
    
    	}
    		else {
        	
    			throw new CustomerNotFoundException(idNotFound);
           }
        	
    }

	public CustomerDTO getCustomerWithIds( Integer customerId) throws CustomerNotFoundException {
		Customer customer ;

			 Optional<Customer> customerInRepo =  customerRepository.findById(customerId);
			 if (customerInRepo.isPresent()) {
		    		customer = customerInRepo.get();
		    		LOGGER.info("customer is present "+customer.getCustomerName());
			    	 return  this.convertToDto(customer);
			 }
			    	 else {
			 	    	LOGGER.info("the customerId doesnot exist");
			 	    	throw new CustomerNotFoundException(idNotFound);
			 	    
			 	    }
		  }

	/**************************************************************************************************************************************
	 * @param customer (Entity)
	 * @return  CustomerDTO
	 **************************************************************************************************************************************/

	public CustomerDTO convertToDto(Customer customer) {

		// converting into DTO
		return CustomerDTO.builder()
				.customerId(customer.getCustomerId())
				.customerName(customer.getCustomerName())
				.email(customer.getEmail())
				.build();
	}

}
		
	

