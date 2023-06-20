package com.wipro.bank.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.wipro.bank.Model.Customer;

@SpringBootTest
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class TestRepository {
	
	@Autowired
	Customer_Repository repo; 
	
	static Customer customer;
	
	static
	{
		customer = new Customer(1,"Demo","DEMO",1234567890,"demo@demo.com");
	}
	
	@Test
	@Order(1)
	void createCustomer()
	{
		
		Customer customer = new Customer();
		customer.setFirstName("Support");
		customer.setLastName("SUPPORT");
		customer.setEmail("support@support.com");
		
		Customer customerRepository=repo.save(customer);
		assertEquals(1,customerRepository.getCustomerId());
	}
	
	@Test
	@Order(2)
	public void getAll()
	{
		List<Customer> customer = repo.findAll();
		assertEquals(5,customer.size());
	}
	
	@Test
	@Order(3)
	public void getById()
	{
		Customer getcustomer = repo.findById(customer.getCustomerId()).get();
		assertEquals(getcustomer.toString(), customer.toString());
	}
	
	
}
