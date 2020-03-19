package com.enigma.ewalletdemo.responses;


import com.enigma.ewalletdemo.entities.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
	private Long id;
	private Date transactionDate;
	private TransactionType type;
	private String from;
	private String to;
	private BigDecimal amount;
	private String description;

	public TransactionDto(Date transactionDate, String from) {
		this.transactionDate = transactionDate;
		this.from = from;
	}
}
