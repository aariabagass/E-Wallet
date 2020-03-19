package com.enigma.ewalletdemo.repositories;

import com.enigma.ewalletdemo.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	List<Transaction> findByFromAndTransactionDate(String accountNumber, Date date);
}
