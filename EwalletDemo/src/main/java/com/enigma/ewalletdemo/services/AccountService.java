package com.enigma.ewalletdemo.services;

import com.enigma.ewalletdemo.entities.Account;
import com.enigma.ewalletdemo.exeptions.NotFoundException;
import com.enigma.ewalletdemo.repositories.AccountRepository;
import com.enigma.ewalletdemo.responses.AccountBalanceDto;
import com.enigma.ewalletdemo.responses.AccountDto;
import com.enigma.ewalletdemo.responses.AccountPostDto;
import com.enigma.ewalletdemo.responses.AccountStatusDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repo;

    //ConvertAcc
    public Account convertAccount(AccountDto dto) {
        ModelMapper mapper = new ModelMapper();
        Account account = mapper.map(dto, Account.class);
        return account;
    }

    //ConvertDto
    public AccountDto convertAccountDto(Account account) {
        ModelMapper mapper = new ModelMapper();
        AccountDto dto = mapper.map(account, AccountDto.class);
        return dto;
    }

    //UPDATE ACC
    public void updateAccount(Account account) {
        repo.save(account);
    }

    //CREATE
    public AccountDto create(AccountPostDto request) {
        Account checkAccount = repo.findByAccountNo(request.getAccountNo());
        if (checkAccount != null) {
            throw new NotFoundException("Account Number : " + request.getAccountNo() + " Found Cannot Create Account");
        }
        ModelMapper mapper = new ModelMapper();
        Account account = mapper.map(request, Account.class);
        account.setPoint(0.0);
        account.setStatus(0);
        repo.save(account);

        return convertAccountDto(account);
    }

    //GET ACC NO
    public AccountDto getAccountByNumber(String accountNumber) {
        Account account = repo.findByAccountNo(accountNumber);
        if (account == null) {
            throw new NotFoundException("Account Number : " + accountNumber + " Not Found");
        }
        return convertAccountDto(account);
    }

    //GET ACC NAME
    public List<AccountDto> findByName(String name) {
        List<AccountDto> listAccount = new ArrayList<AccountDto>();
        repo.findByName(name).forEach(account -> {
            AccountDto accountDto = convertAccountDto(account);
            listAccount.add(accountDto);
        });
        if (listAccount.size() == 0) {
            throw new NotFoundException("name : " + name + " Not Found");
        }
        return listAccount;
    }

    //ADD BALANCE
    public AccountDto updateBalance(AccountBalanceDto request) {
        AccountDto accountDto = this.getAccountByNumber(request.getAccountNo());
        if (accountDto.getStatus() == 1) {
            throw new NotFoundException("account not active");
        } else if (accountDto.getStatus() > 1) {
            throw new NotFoundException("Status not define");
        }
        Account account = convertAccount(accountDto);
        account.setBalance(account.getBalance().add(request.getBalance()));
        repo.save(account);
        AccountDto accountDtoNew = convertAccountDto(account);
        return accountDtoNew;
    }

    //BalanceMinus
    public void substractBalance(String accountNumber, BigDecimal ammount) {
        AccountDto accountDto = this.getAccountByNumber(accountNumber);
//        if (accountDto.getStatus() == 1) {
//            throw new NotFoundException("account not active");
//        } else if (accountDto.getStatus() > 1) {
//            throw new NotFoundException("Status not define");
//        }
        Account account = convertAccount(accountDto);
        account.setBalance(account.getBalance().subtract(ammount));
        repo.save(account);
    }

    //BalancePlus
    public String addBalance(String accountNumber, BigDecimal ammount) {
        AccountDto accountDto = this.getAccountByNumber(accountNumber);
//        if (accountDto.getStatus() == 1) {
//            throw new NotFoundException("account not active");
//        } else if (accountDto.getStatus() > 1) {
//            throw new NotFoundException("Status not define");
//        }
        Account account = convertAccount(accountDto);
        account.setBalance(account.getBalance().add(ammount));
        repo.save(account);
        return "addBalance";
    }

    //update status
    public AccountDto updateStatus(AccountStatusDto request) {
        AccountDto accountDto = this.getAccountByNumber(request.getAccountNo());
        if (request.getStatus() == 1 || request.getStatus() == 0) {
            if (accountDto.getStatus() == 1) {
                throw new NotFoundException("account not active");
            } else {
                Account account = convertAccount(accountDto);
                account.setStatus(request.getStatus());
                repo.save(account);
                AccountDto accountDtoNew = convertAccountDto(account);
                return accountDtoNew;
            }
        } else {
            throw new NotFoundException("Status not define");
        }
    }
}
