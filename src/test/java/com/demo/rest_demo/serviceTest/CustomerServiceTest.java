package com.demo.rest_demo.serviceTest;

import com.demo.rest_demo.dto.CustomerDTO;
import com.demo.rest_demo.entity.Customer;
import com.demo.rest_demo.repository.CustomerRepository;
import com.demo.rest_demo.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    private static final String CUSTOMER_NAME_TEST= "Test Name";
    private static final String CUSTOMER_EMAIL_ADDRESS = "test_email@abc.com";
    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void whenMapCustomerWithExactMatch_thenConvertsToDTO() {

        Customer customer =  new Customer(100, CUSTOMER_NAME_TEST,CUSTOMER_EMAIL_ADDRESS);
        CustomerDTO customerDTO = this.convertToDTO(customer);

        assertEquals(customer.getCustomerName(),customerDTO.getCustomerName());
        assertEquals(customer.getCustomerId(),customerDTO.getCustomerId());

    }

    private CustomerDTO convertToDTO(Customer customer) {
        // converting into DTO
        return CustomerDTO.builder()
                .customerId(customer.getCustomerId())
                .customerName(customer.getCustomerName())
                .email(customer.getEmail())
                .build();
    }

    @Test
    public void getAllCustomer_success_returnListOfDTO() {
        // Data Preparation
        Customer customer1 = mock(Customer.class);
        Customer customer2 = mock(Customer.class);

        List<Customer> customerEntities = List.of(customer1, customer2);
        // Mock behaviour
        when(customerRepository.findAll()).thenReturn(customerEntities);

        // Actual service testing
        List<CustomerDTO> customerList = customerService.getAllCustomer();

        assertAll("customerList", () -> {
            assertNotNull(customerList);
            assertAll("body",
                    () -> assertThat(customerService.getAllCustomer(), is(notNullValue())),
                    () -> assertThat(customerService.getAllCustomer().size(), is(2)),
                    () -> assertThat(customerService.getAllCustomer().getFirst().getCustomerId(), is(notNullValue()))
            );

        });
    }

//    @Test
//    public void getCustomerById_success_returnMatchingDTO() {
//        // Data Preparation
//        Customer customer1 = mock(Customer.class);
//        Optional<Customer> optionalCustomer = Optional.of(customer1);
//
//        // Mock behaviour
//        when(customerRepository.findById(customer1.getCustomerId()).thenReturn(optionalCustomer));
//
//        // Actual service testing
//        CustomerDTO customerDTO = customerService.getCustomerById();
//
//        assertAll("customerList", () -> {
//            assertNotNull(customerList);
//            assertAll("body",
//                    () -> assertThat(customerService.getAllCustomer(), is(notNullValue())),
//                    () -> assertThat(customerService.getAllCustomer().size(), is(2)),
//                    () -> assertThat(customerService.getAllCustomer().getFirst().getCustomerId(), is(notNullValue()))
//            );
//
//        });
//    }


//    @Test
//    public void saveCustomer_validRequest_shouldCreateProduct(){
//        CustomerDTO customerDTO = CustomerDTO
//                .builder()
//                .customerId(100)
//                .customerName(CUSTOMER_NAME_TEST)
//                .email(CUSTOMER_EMAIL_ADDRESS)
//                .build();
//
//        Customer customerEntity = Customer
//                .builder()
//                .customerId(customerDTO.getCustomerId())
//                .customerName(customerDTO.getCustomerName())
//                .email(customerDTO.getEmail())
//                .build();
//
//
//       // when(customerRepository.save(customerEntity)).thenReturn(customerDTO);
//        }

//
//
//
    

}
