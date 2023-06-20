package com.wipro.bank.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.bank.Exception.ResourceNotCreatedException;
import com.wipro.bank.Exception.ResourceNotFoundException;
import com.wipro.bank.Model.Account;
import com.wipro.bank.Model.Customer;
import com.wipro.bank.Repository.Account_Repository;
import com.wipro.bank.Repository.Customer_Repository;

@Service
public class Bank_Service
{
	@Autowired
	Account_Repository accountRepo;
	
	@Autowired
	Customer_Repository customerRepo;
	
	//ACCOUNT SERVICE METHODS //
	
	//home
	public String homeScreen()
	{
		return "WELCOME to the WIPRO's CAPSTONE ACCOUNT TRACKER PROJECT";
	}
	
	//addnewAccount
	public Account addAccount(Account accountRequest) throws ResourceNotCreatedException
	{
		Account newAccount = accountRepo.save(accountRequest);
		
		return 	newAccount;
	}
	
	//getAccountDetails by Id
	public Account getAccountDetails(long id) throws ResourceNotFoundException
	{
		if(accountRepo.existsById(id))
		{
			Account account = accountRepo.findById(id).get();
			return account;
		}
		else
		{
			throw new ResourceNotFoundException("Account Detail's not Found");
		}		
	}
	
	//getAllAccounts
	public List<Account> getAllAccounts() throws ResourceNotFoundException
	{
		List<Account> accounts = accountRepo.findAll();
		
		if(accounts.isEmpty())
			throw new ResourceNotFoundException("Account's not Found");
			
		return accounts;
	}
	
	//update Account
	public Account editAccount(Account account) throws ResourceNotFoundException
	{
		if(accountRepo.existsById(account.getAccountNo()))
		{
			Account updatedAccount = accountRepo.save(account);
			return updatedAccount;
		}
		else
		{
			throw new ResourceNotFoundException("Account not exist");
		}
	}
	
	//deleteAccount
	public boolean deleteAccount(long id) throws ResourceNotFoundException
	{
		if(accountRepo.existsById(id))
		{
			accountRepo.deleteById(id);
			return true;
		}
		else
		{
			throw new ResourceNotFoundException("Account not exist");
		}		
	}
	
	//transfer Funds
	public String fundTransfer(Long fromAccount, Long toAccount, double amount) throws ResourceNotFoundException
	{
		Account sender = accountRepo.getById(fromAccount);
		Account receiver = accountRepo.getById(toAccount);
		
		if(sender == null || receiver == null)
			throw new ResourceNotFoundException("Either Receiver or Sender account doesn't exist.");
		
		double senderBalance = sender.getAccountbalance();
		double receiverBalance = receiver.getAccountbalance();
		
		if(senderBalance > amount)
		{
			sender.setAccountbalance(senderBalance - amount);
			receiver.setAccountbalance(receiverBalance + amount);
			accountRepo.save(sender);
			accountRepo.save(receiver);
			
			return "Fund Transfer Successful";
		}
		else
			throw new ResourceNotFoundException("Insufficent Balance");
	}	
	
	// CUSTOMER SERVICE Methods //
	
	//addnewCustomer
	@Transactional
	public Customer addCustomer(Customer customer)
	{	
		Customer  savedCustomer = customerRepo.save(customer);
		return savedCustomer;	
	}
	
	//GetCustomer Details by Id
	public Customer getCustomerDetails(int id) throws ResourceNotFoundException
	{
		if(customerRepo.existsById(id))
		{
			Customer customerDetails = customerRepo.findById(id).get();
			return customerDetails;
		}
		else
		{
			throw new ResourceNotFoundException("Customer Details's not Found");
		}
	}
	
	//getAllCustomers
	public List<Customer> getAllCustomers() throws ResourceNotFoundException
	{
		List<Customer> customers = customerRepo.findAll();
		
		if(customers.isEmpty())
			throw new ResourceNotFoundException("Customer's not Found");
			
		return customers;
	}
	
	//update Customer
	public Customer editCustomer(Customer customer) throws ResourceNotFoundException
	{
		if(customerRepo.existsById(customer.getCustomerId()))
		{
			Customer updatedCustomer = customerRepo.save(customer);
			return updatedCustomer;
		}
		else
		{
			throw new ResourceNotFoundException("Customer not exist");
		}
	}
	
	//deleteCustomer
	public boolean deleteCustomer(int id) throws ResourceNotFoundException
	{
		if(customerRepo.existsById(id))
		{
			customerRepo.deleteById(id);
			return true;
		}
		else
		{
			throw new ResourceNotFoundException("Customer not exist");
		}		
	}
		
	//add account for existing customer
	public Customer addAccountForExistingCustomer(int id, Account account) throws ResourceNotFoundException
	{
		if(customerRepo.existsById(id))
		{
			Set<Account> accountSet = null;
			Customer customer = customerRepo.findById(id).get();
			
			if(customer.getAccounts().isEmpty())
			{
				accountSet = new HashSet<>();
				accountSet.add(account);
				customer.setAccounts(accountSet);
			}
			else
			{
				accountSet = customer.getAccounts();
				accountSet.add(account);
				customer.setAccounts(accountSet);
			}
			
			Set<Customer> customerSet = new HashSet<Customer>();
			customerSet.add(customer);
			
			Account acc = new Account(id, account.getAccountType(), account.getAccountbalance(), customerSet);
			acc.setCustomer(customerSet);
			
			Customer cus = customerRepo.save(customer);
			
			return cus;
		}
		else
		{
			throw new ResourceNotFoundException("Customer not Found with Id: " + id);
		}	
	}
}