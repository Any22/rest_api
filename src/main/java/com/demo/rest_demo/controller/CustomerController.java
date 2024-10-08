package com.demo.rest_demo.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.rest_demo.dto.CustomerDTO;
import com.demo.rest_demo.entity.Customer;
import com.demo.rest_demo.exception.CustomerNotFoundException;
import com.demo.rest_demo.exception.MethodArgumentNotValidException;
import com.demo.rest_demo.exception.NoDataFoundException;
import com.demo.rest_demo.service.CustomerService;
import com.demo.rest_demo.util.RestDemoConstant;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;


/*****************************************************************************************************************************
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
 *   
 *   @Inject(can be used in place of @Autowired) If you are developing a Java EE or Jakarta EE application, or if you want to
 *    follow the JSR-330 standard for dependency injection, then @Inject is the more appropriate choice.
 *   
 *   getStackTrace() provides detailed information about the call stack, which can be useful for debugging and analyzing 
 *   the flow of program execution when an exception occurs. On the other hand, getMessage() provides a human-readable 
 *   description of the exception, conveying information about what went wrong in a more user-friendly way. Depending on 
 *   the context, you might use one or both of these methods when dealing with exceptions.
 *   
 *   Versioning is implemented in the handler method below using three ways 
 *  
 *   

 ***************************************************************************************************************************/
@Validated    //trigger for URI parameters validation
@RestController
//urls which differs by host/domain, portnumber and schemes(http) are cross origin 
@RequestMapping("/customers")
@CrossOrigin
public class CustomerController {
	
	private static final Log LOGGER=LogFactory.getLog(CustomerController.class) ;
	
	
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired 
	Environment environment;
	
	/***************************************************************************************************************
	 * Creating a new data object and returning an acknowledgment string message that customer DTO has been created 
	 * @param customerDTO
	 * @return A string message 
	 * @throws MethodArgumentNotValidException 
	 ***************************************************************************************************************/
	
	@PostMapping(value = "/create", consumes = {"application/json"})
    public ResponseEntity<String> createCustomer(@Valid @RequestBody CustomerDTO customerDTO ,Errors errors) throws MethodArgumentNotValidException {
		 
		/******************************************************************************************************
		 * The @Valid annotation will automatically trigger validation for customerDTO
		 * No need to check for null here, as @Valid will handle validation .you don't need to manually throw 
		 * MethodArgumentNotValidException in your controller method
		 ******************************************************************************************************/
		String response = "";
		if (errors.hasErrors()) {
			
			response= errors.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.joining(","));
			throw new MethodArgumentNotValidException(errors);
			
		  } else {
			  
			  	customerService.saveCustomer(customerDTO);   
		        LOGGER.info(" Data for "+ customerDTO.getCustomerName()+ " is saved !");
		        return new ResponseEntity<>("Data created for : "+ customerDTO.getCustomerName(), HttpStatus.CREATED);
		  }
		 
     
    }
	
	/*****************************************************************************************************************
	 * Fetching the list of customers from the database.
	 * @return A list of customerDTO
	 * http://localhost:8083/customerMS/customers/v1/get
	 *****************************************************************************************************************/
	 @GetMapping(produces= APPLICATION_JSON_VALUE, value = "v1/get")
	    public ResponseEntity<List<CustomerDTO>> getCustomer() throws NoDataFoundException {
		 
	       try { 
		 	List<CustomerDTO> customerDto = customerService.getAllCustomer();
	        
		        if (customerDto.isEmpty() || null == customerDto) {
		        	
		        	throw new NoDataFoundException("No customers are found in database");
		            
		        } 
	       	
		        return new ResponseEntity<>(customerDto, HttpStatus.OK);
	       } catch (Exception ex) {
			   LOGGER.error(ex.getStackTrace(),ex);
	    	   throw ex;
		   }
	        
	    }
	 
	 @GetMapping(produces= APPLICATION_JSON_VALUE, value = "v2/get")
	    public String getCustomerv2() throws NoDataFoundException {
			
		 /**************************************************************************************
		  * Logic can be implemented here that can fetch another information related to customer
		  * the idea behind this controller is to implement URI versioning
		  *************************************************************************************/
		 
		 
		 return "version2 URI (Demo)"; 
	        
	    }
	 
	  @GetMapping(value = "v3/get" , headers="X-API-VERSION=1")
	    public String getCustomerv3() throws NoDataFoundException {
			
		 /**************************************************************************************
		  * Logic can be implemented here that can fetch another information related to customer
		  * the idea behind this controller is to implement versioning with custom header
		  *************************************************************************************/
		 
		 
		 return "version3 customer headers(Demo)"; 
	        
	    }
	 
	  /*****************************************************************************************
	   * @param customerId
	   * @return
	   * @throws CustomerNotFoundException
	   * URI : http://localhost:8083/customerMS/customers/v1/get/102
	   ****************************************************************************************/
	  
@GetMapping( produces= APPLICATION_JSON_VALUE, value = "v1/get/{customerId}" )
	    public ResponseEntity<CustomerDTO> getCustomerById(@Valid @PathVariable Integer customerId) throws CustomerNotFoundException {
		 
		    LOGGER.info("CUSTOMER id "+ customerId);
	try {
	        CustomerDTO customerDto = customerService.getCustomerById(customerId);
	      
	        
	        if (null == customerDto) {
	        	
	        	throw new CustomerNotFoundException(environment.getProperty(RestDemoConstant.CUSTOMER_NOT_FOUND.toString()));
	            
	        } 
	        	return new ResponseEntity<>(customerDto, HttpStatus.OK);
	        
	    } catch(Exception ee) {
	    	throw ee;
	      }
}
	 
	/*********************************************************************************************
	 * @param customerId
	 * @return
	 * @throws CustomerNotFoundException
	 *  URI : http://localhost:8083/customerMS/customers/get/107?version=2
	 *********************************************************************************************/
	 
	 @GetMapping( produces= APPLICATION_JSON_VALUE, value = "/get/{customerId}" ,params="version=2" )
	    public String getCustomerByIdV1(@PathVariable Integer customerId) throws CustomerNotFoundException {
		 
		    
	        	return "to demonstrate the request parameter versioning";
	        
	    }
	 
	 
	 @PutMapping(consumes = APPLICATION_JSON_VALUE,value = "update/{customerId}")
	 
	 public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("customerId") Integer customerId, @RequestBody CustomerDTO customerDto)
			 throws ConstraintViolationException, CustomerNotFoundException{
	
		 
		 return ResponseEntity.status(HttpStatus.OK).body(customerService.updateCustomer(customerId,customerDto ));
		 
	 }
	
     @DeleteMapping (value= {"/delete/{customerId}"})
     
     public ResponseEntity<String> deleteCustomer( @PathVariable Integer customerId) throws CustomerNotFoundException {
    	 
    	  if (null == customerId || customerId.equals(0)) {
 			 
			  return new ResponseEntity<String>("The id don't exist",HttpStatus.BAD_REQUEST);
		  }
    	  
    	  customerService.deleteCustomer(customerId);
    	  return new ResponseEntity<>("deleted : "+ customerId, HttpStatus.OK);
    	 
     }


}



