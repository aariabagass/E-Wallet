package com.enigma.ewalletdemo.controllers;

import com.enigma.ewalletdemo.exeptions.ErrorDetails;
import com.enigma.ewalletdemo.responses.AccountBalanceDto;
import com.enigma.ewalletdemo.responses.AccountDto;
import com.enigma.ewalletdemo.responses.AccountPostDto;
import com.enigma.ewalletdemo.responses.AccountStatusDto;
import com.enigma.ewalletdemo.services.AccountService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@EnableAutoConfiguration
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountService services;

    //GET ACC NO
    @GetMapping("/{accountNumber}")
    public AccountDto getAccountByNumber(@PathVariable String accountNumber) {
        return services.getAccountByNumber(accountNumber);
    }


    //GET ACC NAME
    @GetMapping("/search")
    public List<AccountDto> searchAccountByName(@RequestParam String name) {
        return services.findByName(name);
    }

    //CREATE ACC
    @PostMapping("")
    public AccountDto createAccount(@Valid @RequestBody AccountPostDto request,
                                  UriComponentsBuilder builder) {
        return services.create(request);
    }

    //ADD BALANCE
    @PutMapping("/balance")
    public AccountDto addBalance(@Valid @RequestBody AccountBalanceDto request,
                                    UriComponentsBuilder builder) {
        return services.updateBalance(request);
    }

    //CHANGE STATUS
    @PutMapping("/status")
    public AccountDto changeStatus(@Valid @RequestBody AccountStatusDto request,
                                   UriComponentsBuilder builder) {
        return services.updateStatus(request);
    }
}
