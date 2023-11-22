package com.demo.rest_demo.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.rest_demo.dto.CustomerDTO;
import com.demo.rest_demo.entity.Customer;
import com.demo.rest_demo.service.CustomerService;


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

 **********************************************************************************************************/

@RestController
@CrossOrigin

public class CustomerController {
	
	private static final Log LOGGER = LogFactory.getLog(CustomerController.class);
	
	@Autowired
	private CustomerService customerService;
	
	
	@PostMapping("/customers")
    public ResponseEntity<String> createCustomer(@RequestBody CustomerDTO customerDTO) {
	
		  if ( null == customerDTO) {
			 
			  return new ResponseEntity<String>("check data fields",HttpStatus.NO_CONTENT);
		  }
	    
        Customer createdCustomer = customerService.saveCustomer(customerDTO);
        
        
        LOGGER.info(" Data for "+ customerDTO.getCustomerName()+ "is saved !");
   
       
        return new ResponseEntity<>("Data created for : ", HttpStatus.CREATED);
    }
	
	 @GetMapping("/customers")
	    public ResponseEntity<List<CustomerDTO>> getCustomer() {
		 
	        List<CustomerDTO> customerDto = customerService.getAllCustomer();
	        
	        if (customerDto.isEmpty() || null == customerDto) {
	        	
	        	return ResponseEntity.notFound().build();
	            
	        } 
	        	return new ResponseEntity<>(customerDto, HttpStatus.OK);
	        
	    }
	 

	


//	updateCustomers();
//	deleteCustomers();

}
