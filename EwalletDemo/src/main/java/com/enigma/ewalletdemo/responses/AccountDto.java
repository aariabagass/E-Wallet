package com.enigma.ewalletdemo.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private String accountNo;
    private String name;
    private BigDecimal balance;
    private Double point;
    private Integer status;

    //No ID
    public AccountDto(String accountNo, String name, BigDecimal balance, Double point, Integer status) {
        super();
        this.accountNo = accountNo;
        this.name = name;
        this.balance = balance;
        this.point = point;
        this.status = status;
    }
}
