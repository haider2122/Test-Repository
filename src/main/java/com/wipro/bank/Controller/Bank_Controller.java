package com.wipro.bank.Controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.bank.Exception.ResourceNotCreatedException;
import com.wipro.bank.Exception.ResourceNotFoundException;
import com.wipro.bank.Model.Account;
import com.wipro.bank.Model.Customer;
import com.wipro.bank.Service.Bank_Service;

@RestController
public class Bank_Controller
{
	@Autowired
	Bank_Service bankService;
	
	@GetMapping("/")
	public String helloWorld()
	{
		return bankService.homeScreen();
	}
	
	@PostMapping("/account/add")
	public ResponseEntity<Account> addAccount(@RequestBody Account account) throws ResourceNotCreatedException
	{
		if(account.getAccountType() == null || account.getAccountbalance() < 0.0)
			throw new ResourceNotCreatedException("Incorrect Details to open an Account.");
		
		Account newAccount = bankService.addAccount(account);
		
		return new ResponseEntity<Account>(newAccount,HttpStatus.CREATED);
	}
	
	@GetMapping("/account/{id}")
	public ResponseEntity<Account> getAccountDetails(@PathVariable long id) throws ResourceNotFoundException
	{
		Account accountDetails = bankService.getAccountDetails(id);
		
		if(accountDetails == null)
			throw new ResourceNotFoundException("Account Details not found");
		
		return new ResponseEntity<Account>(accountDetails,HttpStatus.FOUND);	
	}
	
	@GetMapping("/account/all")
	public ResponseEntity<List<Account>> getAllAccountsDetails() throws ResourceNotFoundException
	{
		List<Account> accountDetails = bankService.getAllAccounts();
		
		if(accountDetails.isEmpty())
			throw new ResourceNotFoundException("No Account's found");
		
		return new ResponseEntity<List<Account>>(accountDetails,HttpStatus.FOUND);	
	}
	
	@PutMapping("/account/update/{id}")
	public ResponseEntity<Account> updateAccount(@PathVariable long id) throws ResourceNotFoundException {
		
		Account accountDetails = bankService.getAccountDetails(id);
		
		if(accountDetails == null)
			throw new ResourceNotFoundException("Account Details are Incorrect");
		
		return new ResponseEntity<Account>(accountDetails,HttpStatus.OK);		
	}
	
	@DeleteMapping("/account/delete/{id}")
	public ResponseEntity<Void> deleteAccount(@PathVariable long id) throws ResourceNotFoundException {
		
		boolean deleteAccount = bankService.deleteAccount(id);
		
		if(deleteAccount)
			return  new ResponseEntity<Void>(HttpStatus.OK);
		else
			throw new ResourceNotFoundException("Account not found with Id: " + id);
	}
	
	@PutMapping("/account/transfer/{fromAccount}/{toAccount}/{amount}")
	public ResponseEntity<?> transferFunds(@PathVariable long fromAccount, @PathVariable long toAccount, @PathVariable double amount) throws ResourceNotFoundException, ResourceNotCreatedException
	{
		if(amount <= 0)
			throw new ResourceNotFoundException("Amount should be greater than zero.");
		else
			return new ResponseEntity<>(bankService.fundTransfer(fromAccount, toAccount, amount) ,HttpStatus.OK);	
	}
	
	@PostMapping("/customer/add")
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) throws ResourceNotCreatedException
	{
		if(customer.getPhone() == 0 || customer.getEmail() == null || customer.getFirstName() == null)
			throw new ResourceNotCreatedException("Incorrect Details of Customer.");
		
		Customer newCustomer = bankService.addCustomer(customer);
		
		return new ResponseEntity<Customer>(newCustomer,HttpStatus.CREATED);
	}
	
	@GetMapping("/customer/{id}")
	public ResponseEntity<Customer> getCustomerDetails(@PathVariable int id) throws ResourceNotFoundException
	{
		Customer customerDetails = bankService.getCustomerDetails(id);
		
		if(customerDetails == null)
			throw new ResourceNotFoundException("Account Details not found");
		
		return new ResponseEntity<Customer>(customerDetails,HttpStatus.FOUND);	
	}
	
	@GetMapping("/customer/all")
	public List<Customer> getAllCustomers() throws ResourceNotFoundException
	{
		List<Customer> customer = bankService.getAllCustomers();
		return customer;
	}
	
	@PutMapping("/customer/update/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable int id) throws ResourceNotFoundException {
		
		Customer customerDetails = bankService.getCustomerDetails(id);
		
		if(customerDetails == null)
			throw new ResourceNotFoundException("Customer Details are Incorrect");
		
		return new ResponseEntity<Customer>(customerDetails,HttpStatus.OK);		
	}
	
	@DeleteMapping("/customer/delete/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable int id) throws ResourceNotFoundException {
		
		boolean deleteCustomer = bankService.deleteCustomer(id);
		
		if(deleteCustomer)
			return  new ResponseEntity<Void>(HttpStatus.OK);
		else
			throw new ResourceNotFoundException("Customer not found with Id: " + id);
	}
	
	@PostMapping("/customers/{id}/accounts")
	public ResponseEntity<Customer> addAccountForExistingCustomer(@PathVariable int id, @RequestBody Account account) throws ResourceNotFoundException
	{
		Customer customer = bankService.addAccountForExistingCustomer(id, account);
		
		return new ResponseEntity<Customer>(customer,HttpStatus.CREATED);	
	}
	
}