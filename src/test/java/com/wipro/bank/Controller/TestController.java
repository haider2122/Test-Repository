package com.wipro.bank.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.wipro.bank.Model.Account;
import com.wipro.bank.Model.Customer;
import com.wipro.bank.Repository.Account_Repository;
import com.wipro.bank.Service.Bank_Service;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(Bank_Controller.class)
@TestMethodOrder(OrderAnnotation.class)
public class TestController {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	Account_Repository repo;
	
	static Account account;
	
	@MockBean
	private Bank_Service service;
	
	@Test
	@Order(1)
	void test() throws Exception
	{
		RequestBuilder request;
		List<Customer> asList = Arrays.asList(
				new Customer(1,"Demo","DEMO",1234567890,"demo@demo.com"),
				new Customer(2,"Support","SUPPORT",987654321,"support@support.com"));
		
		when(service.getAllCustomers()).thenReturn(asList);
		
		request = MockMvcRequestBuilders
				.get("/customers")
				.accept(MediaType.APPLICATION_JSON);
		
		String expectedResult= "[{customerId : 1, firstName : Demo, lastName : DEMO, phone : 1234567890, email : demo@demo.com, accounts : []},{customerId : 2, firstName : Support, lastName : SUPPORT, phone : 987654321, email : support@support.com, accounts:[]}]";
		MvcResult result = mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().json(expectedResult))
				.andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());		
	}
	
	@Test
	@Order(2)
	public void deleteAccount()
	{
		Set<Customer> customer = new HashSet<>();
		customer.add(	new Customer(1,"Demo","DEMO",1234567890,"demo@demo.com"));
		account = new Account();
		repo.save(account);
		
		account = new Account();
		repo.deleteAll();
	
		
		List<Account> account = repo.findAll();
		assertEquals(3,account.size());
		
		repo.deleteById((long) 3);
		account = repo.findAll();
		assertEquals(2, account.size());
	}
}
