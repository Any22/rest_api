package com.demo.rest_demo.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.rest_demo.dto.CustomerDTO;
import com.demo.rest_demo.entity.Customer;
import com.demo.rest_demo.exception.CustomerNotFoundException;
import com.demo.rest_demo.service.CustomerService;
import com.demo.rest_demo.util.RestDemoConstant;

import jakarta.validation.Valid;


/**********************************************************************************************************
 * Use Noun to represent the resources not verbs like /customer 
 * ResponseEntity class fine tune the Http responses (Response header + response body)
 * 
 * using ResponseEntity class we can set the body, status and header of a Http response .
 * Status can be : ok()         ---> 200 code    (Static methods)
 *                 ok(T Body)   ---> 201
 *                 badRequest() ---> 400
 *                 notFound()   ---> 404
 *                 status(int status) ---> Sets the given status code value
 *                 status(HTTPStatus status) -----> Sets the status code using HttpStaus enum
 *                 
 *   Response Entity (Constructors)
 *  1. ResponseEntity(Http status) creates a response entity object with the given status code 
 *   and no body 
 *  2. ResponseEntity(MultiValueMap headers, HttpStatus status)  creates a ResponseEntity object 
 *   with headers and status code  but NO BODY  
 *  3. ResponseEntity(T body, HttpStatus status)  ResponseEntity object with status code and body       
 *  4. ResponseEntity(T body , MultiValueMap headers, HttpStatus status)  ResponseEntity object with status 
 *  code , body and headers       
 *   
 *   Annotations used 
 *   @ResquestBody helps in de-serializing the incoming customer data into CustomerDTO
 *   @Validated annotations trigger the data validation on request parameters/URI parameters 

 **********************************************************************************************************/
@Validated 
@RestController
@CrossOrigin
public class CustomerController {
	
	private static final Log LOGGER = LogFactory.getLog(CustomerController.class);
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired 
	Environment environment;
	
	/***************************************************************************************************************
	 * Creating a new data object and returning an acknowledgment string message that customer DTO has been created 
	 * @param customerDTO
	 * @return A string message 
	 ***************************************************************************************************************/
	
	@PostMapping(value = "/customers", consumes = {"application/json"})
    public ResponseEntity<String> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
	
		  if ( null == customerDTO) {
			 
			  return new ResponseEntity<String>("check data fields",HttpStatus.NO_CONTENT);
		  }
	    
        Customer createdCustomer = customerService.saveCustomer(customerDTO);   
        LOGGER.info(" Data for "+ createdCustomer.getCustomerName()+ " is saved !");
   
       
        return new ResponseEntity<>("Data created for : "+ customerDTO.getCustomerName(), HttpStatus.CREATED);
    }
	
	/*****************************************************************************************************************
	 * Fetching the list of customers from the database.
	 * @return A list of customerDTO
	 * 
	 *****************************************************************************************************************/
	 @GetMapping(value = "/customers", produces= {"application/json"})
	    public ResponseEntity<List<CustomerDTO>> getCustomer() {
		 
	        List<CustomerDTO> customerDto = customerService.getAllCustomer();
	        
	        if (customerDto.isEmpty() || null == customerDto) {
	        	
	        	return ResponseEntity.notFound().build();
	            
	        } 
	        	return new ResponseEntity<>(customerDto, HttpStatus.OK);
	        
	    }
	 
	 @GetMapping(value = "/customers/{customerId}", produces= {"application/json"})
	    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer customerId) throws CustomerNotFoundException {
		 
		    LOGGER.info("CUSTOMER id "+ customerId);
	        CustomerDTO customerDto = customerService.getCustomerById(customerId);
	      
	        
	        if (null == customerDto) {
	        	
	        	throw new CustomerNotFoundException(environment.getProperty(RestDemoConstant.CUSTOMER_NOT_FOUND.toString()));
	            
	        } 
	        	return new ResponseEntity<>(customerDto, HttpStatus.OK);
	        
	    }
	 
	 
	 @PutMapping(value = "/customers/{customerId}", consumes = {"application/json"})
	 
	 public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("customerId") Integer customerId, @RequestBody CustomerDTO customerDto)
			 throws CustomerNotFoundException{
	
		 return ResponseEntity.status(HttpStatus.OK).body(customerService.updateCustomer(customerId,customerDto ));
		 
	 }
	
     @DeleteMapping (value= {"/customers/{customerId}"})
     
     public ResponseEntity<String> deleteCustomer( @PathVariable Integer customerId) {
    	 
    	  if (null == customerId || customerId.equals(0)) {
 			 
			  return new ResponseEntity<String>("The id don't exist",HttpStatus.BAD_REQUEST);
		  }
    	  
    	  customerService.deleteCustomer(customerId);
    	  return new ResponseEntity<>("deleted : "+ customerId, HttpStatus.OK);
    	 
     }


}
