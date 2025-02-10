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
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    private static final String CUSTOMER_NAME_TEST= "Test Name";
    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void whenMapCustomerWithExactMatch_thenConvertsToDTO() {

        Customer customer =  new Customer(100, CUSTOMER_NAME_TEST,"sophie@abcnetwork.com");
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

    @Test
    public void saveCustomer_validRequest_shouldCreateProduct(){

        when(customerRepository.save(any())).thenReturn(CustomerDTO.builder().customerId(100).customerName("TestName").email("test_email@abc.com").build());

}




}
