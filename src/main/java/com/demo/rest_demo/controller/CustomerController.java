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

import org.springframework.web.bind.annotation.*;

import com.demo.rest_demo.dto.CustomerDTO;

import com.demo.rest_demo.exception.CustomerNotFoundException;
import com.demo.rest_demo.exception.MethodArgumentNotValidException;
import com.demo.rest_demo.exception.NoDataFoundException;
import com.demo.rest_demo.service.CustomerService;
import com.demo.rest_demo.util.RestDemoConstant;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@Validated                            //trigger for URI parameters validation
@RestController
@RequestMapping("/customers")
@CrossOrigin
//urls which differs by host/domain, port number and schemes(http) are cross origin
public class CustomerController {

    private static final Log LOGGER = LogFactory.getLog(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    Environment environment;

    /******************************************************************************************************************
     * Creating a new data object and returning an acknowledgment string message that customer DTO has been created
     * @return A string message
     *****************************************************************************************************************/

    @PostMapping(value = "/create", consumes = {"application/json"})
    public ResponseEntity<String> createCustomer(@Valid @RequestBody CustomerDTO customerDTO, Errors errors) throws MethodArgumentNotValidException {

        String response = "";
        if (errors.hasErrors()) {

            response = errors.getAllErrors().stream().map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(","));
            throw new MethodArgumentNotValidException(errors);

        } else {

            customerService.saveCustomer(customerDTO);
            LOGGER.info(" Data for " + customerDTO.getCustomerName() + " is saved !");
            return new ResponseEntity<>("Data created for : " + customerDTO.getCustomerName(), HttpStatus.CREATED);
        }


    }

    /*****************************************************************************************************************
     * Fetching the list of customers from the database.
     * @return A list of customerDTO
     * <a href="http://localhost:8083/customerMS/customers/v1/get">...</a>
     *****************************************************************************************************************/
    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "v1/get")
    public ResponseEntity<List<CustomerDTO>> getCustomer() throws NoDataFoundException {

        try {
            List<CustomerDTO> customerDto = customerService.getAllCustomer();

            if (customerDto.isEmpty()) {
                throw new NoDataFoundException("No customers are found in database");
            }

            return new ResponseEntity<>(customerDto, HttpStatus.OK);
        } catch (Exception ex) {
            LOGGER.error(ex.getStackTrace(), ex);
            throw ex;
        }

    }

    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "v2/get")
    public String getCustomerv2() throws NoDataFoundException {
        /*************************************************************************************************************
         * Logic can be implemented here that can fetch another information related to customer
         * the idea behind this controller is to implement URI versioning
         * ***********************************************************************************************************/


        return "version2 URI (Demo)";

    }

    @GetMapping(value = "v3/get", headers = "X-API-VERSION=1")
    public String getCustomerv3() throws NoDataFoundException {

        /*************************************************************************************************************
         * Logic can be implemented here that can fetch another information related to customer
         * the idea behind this controller is to implement versioning with custom header
         *************************************************************************************************************/


        return "version3 customer headers(Demo)";

    }

    /******************************************************************************************************************
     * @param customerId
     * @return CustomerDTO
     * @throws CustomerNotFoundException
     * URI : <a href="http://localhost:8083/customerMS/customers/v1/get/102">...</a>
     ******************************************************************************************************************/

    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "v1/get/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@Valid @RequestParam Integer customerId) throws CustomerNotFoundException {

        LOGGER.info("CUSTOMER id " + customerId);
        try {
            CustomerDTO customerDto = customerService.getCustomerById(customerId);

            if (null == customerDto) {

                throw new CustomerNotFoundException(environment.getProperty(RestDemoConstant.CUSTOMER_NOT_FOUND.toString()));

            }
            return new ResponseEntity<>(customerDto, HttpStatus.OK);

        } catch (Exception ee) {
            throw ee;
        }
    }

    /*******************************************************************************************************************
     * @return String message
     * @throws CustomerNotFoundException
     *  URI : <a href="http://localhost:8083/customerMS/customers/get/107?version=2">...</a>
     ******************************************************************************************************************/

    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "/get/{customerId}", params = "version=2")
    public String getCustomerByIdV1(@PathVariable Integer customerId) throws CustomerNotFoundException {


        return "to demonstrate the request parameter versioning";

    }


    @PutMapping(consumes = APPLICATION_JSON_VALUE, value = "update/{customerId}")

    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("customerId") Integer customerId, @RequestBody CustomerDTO customerDto)
            throws ConstraintViolationException, CustomerNotFoundException {


        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateCustomer(customerId, customerDto));

    }

    @DeleteMapping(value = {"/delete/{customerId}"})

    public ResponseEntity<String> deleteCustomer(@PathVariable Integer customerId) throws CustomerNotFoundException {

        if (null == customerId || customerId.equals(0)) {

            return new ResponseEntity<String>("The id don't exist", HttpStatus.BAD_REQUEST);
        }

        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>("deleted : " + customerId, HttpStatus.OK);

    }


}



