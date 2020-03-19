package com.enigma.ewalletdemo.services;

import com.enigma.ewalletdemo.entities.Account;
import com.enigma.ewalletdemo.entities.Transaction;
import com.enigma.ewalletdemo.entities.TransactionType;
import com.enigma.ewalletdemo.exeptions.NotFoundException;
import com.enigma.ewalletdemo.repositories.AccountRepository;
import com.enigma.ewalletdemo.repositories.TransactionRepository;
import com.enigma.ewalletdemo.responses.*;
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

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class TransactionServiceTest {
    @TestConfiguration
    static class TransactionServiceContextConfiguration {
        @Bean
        public TransactionService transactionService() {
            return new TransactionService();
        }

        @Bean
        public AccountService accountService() {
            return new AccountService();
        }
    }

    @Autowired
    TransactionService services;

    @Autowired
    AccountService servicesA;

    @MockBean
    TransactionRepository repo;

    @MockBean
    AccountRepository repoA;

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        Account account = new Account("999999", "bagas", BigDecimal.valueOf(1000000), 50000.0, 0);
        List<AccountDto> listDtoA = new ArrayList<>();
        List<Account> listAccount = new ArrayList<>();
        listAccount.add(account);
        AccountDto dtoA = servicesA.convertAccountDto(account);
        listDtoA.add(dtoA);
        Mockito.when(repoA.findByAccountNo("999999")).thenReturn(account);
        Mockito.when(repoA.findByName("bagas")).thenReturn(listAccount);

        Account account2 = new Account("777777", "satria", BigDecimal.valueOf(2000000), 0.0, 0);
        List<AccountDto> listDtoA2 = new ArrayList<>();
        List<Account> listAccount2 = new ArrayList<>();
        listAccount.add(account2);
        AccountDto dtoA2 = servicesA.convertAccountDto(account2);
        listDtoA2.add(dtoA2);
        Mockito.when(repoA.findByAccountNo("777777")).thenReturn(account2);
        Mockito.when(repoA.findByName("satria")).thenReturn(listAccount2);

        Transaction transaction = new Transaction(1L, Date.valueOf("2020-01-01"), TransactionType.Pulsa, "999999", "777777", BigDecimal.valueOf(50000), "beli jajan");
        List<TransactionDto> listDto = new ArrayList<>();
        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);

        TransactionDto dto = services.convertTransactionDto(transaction);
        listDto.add(dto);
        Mockito.when(repoA.save(account)).thenReturn(account);
        Mockito.when(repo.save(transaction)).thenReturn(transaction);
        Mockito.when(repo.findByFromAndTransactionDate("999999", Date.valueOf("2020-01-01"))).thenReturn(listTransaction);
    }

    @Test
    public void getTest() {
        TransactionDto request = new TransactionDto(Date.valueOf("2020-01-01"), "999999");
        assertEquals(services.getListTransactionByAccountNumberAndDate(request.getFrom(), Date.valueOf("2020-01-01")).size(), 1);
    }

    @Test
    public void addBalance() {
        BigDecimal balance = BigDecimal.valueOf(10000);
        TransactionBalanceDto request = new TransactionBalanceDto("999999", balance);
        assertEquals(services.postBalance(request), "TransactionBalanceDto");
    }

    @Test
    public void transferTest() {
        BigDecimal balance = BigDecimal.valueOf(10000);
        TransactionTransferDto request = new TransactionTransferDto("999999", "777777", balance, "beli jajan");
        assertEquals(services.postTransfer(request), "postTransfer");
    }


    @Test
    public void transferPointTest() {
        TransactionPointDto request = new TransactionPointDto("999999");
        assertEquals(services.postPoint(request), "tranfer point from " + request.getAccountNo() + " success");
    }

    @Ignore
    @Test
    public void PulsaTest() {
        TransactionPulsaDto request = new TransactionPulsaDto("999999", BigDecimal.valueOf(10000), "08133");

//        assertEquals(services.postPulsa(request), "pulsa to " + request.getNohp() + " success , you got " + point + " point");
    }

}
