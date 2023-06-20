package com.wipro.bank.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.bank.Model.Customer;

@Repository
public interface Customer_Repository extends JpaRepository<Customer, Integer>  {

}
