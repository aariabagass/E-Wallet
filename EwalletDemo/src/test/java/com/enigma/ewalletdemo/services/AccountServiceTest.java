package com.enigma.ewalletdemo.services;


import com.enigma.ewalletdemo.entities.Account;
import com.enigma.ewalletdemo.responses.*;
import com.enigma.ewalletdemo.services.AccountService;
import com.enigma.ewalletdemo.repositories.AccountRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Spring;

@RunWith(SpringRunner.class)
public class AccountServiceTest {

    @TestConfiguration
    static class AccountServiceContextConfiguration {
        @Bean
        public AccountService accountServices() {
            return new AccountService();
        }
    }

    @Autowired
    AccountService services;

    @MockBean
    AccountRepository repo;

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        Account account = new Account("999999", "bagas", BigDecimal.valueOf(1000000), 0.0, 0);
        List<AccountDto> listDto = new ArrayList<>();
        List<Account> listAccount = new ArrayList<>();
        listAccount.add(account);
        AccountDto dto = services.convertAccountDto(account);
        listDto.add(dto);
        Mockito.when(repo.findByAccountNo("999999")).thenReturn(account);
        Mockito.when(repo.findByName("bagas")).thenReturn(listAccount);
    }

    @Test
    public void createTest() {
        BigDecimal balance = BigDecimal.valueOf(1000000);
        AccountPostDto request = new AccountPostDto("888888", "bagas", balance);
        assertEquals(services.create(request).getAccountNo(), "888888");
    }

    @Test
    public void findByAccountNumberTest() {
        assertNotNull(services.getAccountByNumber("999999"));
    }

    @Test
    public void findByAccountByNameTest() {
        assertNotNull(services.findByName("bagas"));
    }

    @Test
    public void updateBalanceTest() {
        BigDecimal balance = BigDecimal.valueOf(1000000);
        AccountBalanceDto request = new AccountBalanceDto("999999", balance);
        assertEquals(services.updateBalance(request).getAccountNo(), "999999");
    }

    @Test
    public void updateStatusTest() {
        AccountStatusDto request = new AccountStatusDto("999999", 0);
        assertEquals(services.updateStatus(request).getAccountNo(), "999999");
    }

    @Ignore
    @Test
    public void addBalance() {

    }

}