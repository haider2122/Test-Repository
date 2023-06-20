package com.wipro.bank.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wipro.bank.Model.Customer;
import com.wipro.bank.Repository.Account_Repository;
import com.wipro.bank.Repository.Customer_Repository;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(MockitoExtension.class) 

public class TestService {

	@InjectMocks
	Bank_Service service;
	
	@Mock
	private Customer_Repository customerRepo;
	
	@Mock
	private Account_Repository accountRepo;
	
	static Set<Customer> customer = new HashSet<>();
	
	@Test
	@Order(1)
	void test()
	{
		Customer customer = new Customer();
		
		customer.setFirstName("Demo");
		customer.setLastName("DEMO");
		customer.setPhone(1234567890);
		customer.setEmail("demo.test@demo.com");
		
		Customer mockcustomer=new Customer();
		mockcustomer.setCustomerId(2);
		mockcustomer.setFirstName("Support");
		mockcustomer.setLastName("SUPPORT");
		mockcustomer.setPhone(1987654321);
		mockcustomer.setEmail("support.test@support.com");
		
		when(customerRepo.save(any(Customer.class))).thenReturn(mockcustomer);
		Customer customerService=service.addCustomer(customer);
		
        verify(customerRepo,atLeastOnce()).save(customer);
		assertEquals(6,customerService.getCustomerId());
	}


}
