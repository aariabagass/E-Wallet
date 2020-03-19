package com.enigma.ewalletdemo.services;

import com.enigma.ewalletdemo.entities.Account;
import com.enigma.ewalletdemo.entities.Transaction;
import com.enigma.ewalletdemo.entities.TransactionType;
import com.enigma.ewalletdemo.exeptions.NotFoundException;
import com.enigma.ewalletdemo.repositories.TransactionRepository;
import com.enigma.ewalletdemo.responses.*;
import com.enigma.ewalletdemo.utils.utilsDate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repo;

    @Autowired
    private AccountService services;

    private utilsDate date = new utilsDate();

    //ConvertDto
    public TransactionDto convertTransactionDto(Transaction transaction) {
        ModelMapper modelMapper = new ModelMapper();
        TransactionDto dto = modelMapper.map(transaction, TransactionDto.class);
        return dto;
    }

    //checkbalance
    public void checkBalance(AccountDto account, BigDecimal ammount) {
        if (account.getBalance().compareTo(ammount) < 0) {
            throw new NotFoundException("Account : " + account.getAccountNo() + " not have sufficient");
        }
    }

    //checkpoint
    public void checkPoint(Account account) {
        if (account.getPoint() == 0.0) {
            throw new NotFoundException("Account : " + account.getAccountNo() + " 0 Point");
        }
    }

    //get point 10%
    public Double getPoint(BigDecimal ammount) {
        Double point = 0.0;
        BigDecimal minimum = BigDecimal.valueOf(9999);
        BigDecimal percent = BigDecimal.valueOf(0.1);
        if (ammount.compareTo(minimum) > 0) {
            point = ammount.multiply(percent).doubleValue();
        }
        if (point > 10000.00) {
            point = 10000.00;
        }
        return point;
    }

    //get by number and date
    public List<TransactionDto> getListTransactionByAccountNumberAndDate(String accountNumber, Date date) {
        List<TransactionDto> list = new ArrayList<>();
        repo.findByFromAndTransactionDate(accountNumber, date).forEach(transaction -> {
            TransactionDto dto = convertTransactionDto(transaction);
            list.add(dto);
        });
        return list;
    }

    //add balance
    public String postBalance(@Valid TransactionBalanceDto request) {
        AccountDto accountDto = services.getAccountByNumber(request.getAccountNo());
        Account account = services.convertAccount(accountDto);
        services.addBalance(request.getAccountNo(), request.getAmmount());
        Transaction transaction = new Transaction(date.dateNow(), TransactionType.Balance, request.getAccountNo(), request.getAmmount(), "add balance", account);
        repo.save(transaction);
        return "TransactionBalanceDto";
    }

    //transfer
    public String postTransfer(@Valid TransactionTransferDto request) {
        AccountDto accountDto = services.getAccountByNumber(request.getAccountNo());
        Account account = services.convertAccount(accountDto);
        services.getAccountByNumber(request.getTo());
        checkBalance(accountDto, request.getAmmount());
        services.substractBalance(request.getAccountNo(), request.getAmmount());
        services.addBalance(request.getTo(), request.getAmmount());
        Transaction transaction = new Transaction(date.dateNow(), TransactionType.Transfer, request.getAccountNo(), request.getTo(), request.getAmmount(), request.getDescription(), account);
        repo.save(transaction);
        return "postTransfer";
    }

    //point
    public String postPoint(@Valid TransactionPointDto request) {
        AccountDto accountDto = services.getAccountByNumber(request.getAccountNo());
        Account account = services.convertAccount(accountDto);
        checkPoint(account);
        BigDecimal point = BigDecimal.valueOf(account.getPoint());
        BigDecimal ammount = account.getBalance().add(point);

        Transaction transaction = new Transaction(date.dateNow(), TransactionType.Point, request.getAccountNo(), ammount, "point to balance", account);
        repo.save(transaction);
        account.setBalance(ammount);
        account.setPoint(0.0);
        services.updateAccount(account);

        String message = "tranfer point from " + request.getAccountNo() + " success";
        return message;
    }

    //pulsa
    public String postPulsa(@Valid TransactionPulsaDto request) {
        AccountDto accountDto = services.getAccountByNumber(request.getAccountNo());
        checkBalance(accountDto, request.getAmmount());
        services.substractBalance(accountDto.getAccountNo(), request.getAmmount());
        Double point = getPoint(request.getAmmount());

        AccountDto accountDtoNew = services.getAccountByNumber(request.getAccountNo());
        Account account = services.convertAccount(accountDtoNew);
        account.setPoint(account.getPoint() + point);
        services.updateAccount(account);

        Transaction transaction = new Transaction(date.dateNow(), TransactionType.Pulsa, request.getAccountNo(), request.getAmmount(), request.getNohp(), account);
        repo.save(transaction);

        String message = "pulsa to " + request.getNohp() + " success , you got " + point + " point";
        return message;
    }
}
