package com.wipro.bank.Model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long accountNo;
	private String accountType;	
	private double accountbalance;
	
	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "accounts", fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Customer> customer;

	//default Constructor
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	//Parameterized Constructor
	public Account(long accountNo, String accountType, double accountbalance, Set<Customer> customer) {
		super();
		this.accountNo = accountNo;
		this.accountType = accountType;
		this.accountbalance = accountbalance;
		this.customer = customer;
	}

	//getters & setters
	public long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public double getAccountbalance() {
		return accountbalance;
	}

	public void setAccountbalance(double accountbalance) {
		this.accountbalance = accountbalance;
	}

	public Set<Customer> getCustomer() {
		return customer;
	}

	public void setCustomer(Set<Customer> customer) {
		this.customer = customer;
	}

	//toString
	@Override
	public String toString() {
		return "Account [accountNo=" + accountNo + ", accountType=" + accountType + ", accountbalance=" + accountbalance
				+ ", customer=" + customer + "]";
	}
}
	
	