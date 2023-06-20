package com.wipro.bank.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.bank.Model.Account;

@Repository
public interface Account_Repository extends JpaRepository<Account, Long>{

}
