package com.enigma.ewalletdemo.repositories;


import com.enigma.ewalletdemo.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

	Account findByAccountNo(String accNo);
	List<Account> findByName(String name);

}
