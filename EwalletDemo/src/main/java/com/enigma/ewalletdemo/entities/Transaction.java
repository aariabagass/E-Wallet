package com.enigma.ewalletdemo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;

	@Column(name = "transaction_date")
	private Date transactionDate;

	@Enumerated(EnumType.STRING)
	private TransactionType type;
	
	@Column(name = "froms")
	private String from;
	
	@Column(name = "tos")
	private String to;
	
	@Column(name = "amount")
	private BigDecimal amount;
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

	//no id
	public Transaction(Date transactionDate, TransactionType type, String from, String to, BigDecimal amount,
			String description, Account account) {
		super();
		this.transactionDate = transactionDate;
		this.type = type;
		this.from = from;
		this.to = to;
		this.amount = amount;
		this.description = description;
		this.account = account;
	}
	// no id, to
	public Transaction(Date transactionDate, TransactionType type, String from, BigDecimal amount,
			String description, Account account) {
		super();
		this.transactionDate = transactionDate;
		this.type = type;
		this.from = from;
		this.amount = amount;
		this.description = description;
		this.account = account;
	}

	public Transaction(Long transactionId, Date transactionDate, TransactionType type, String from, String to, BigDecimal amount, String description) {
		this.transactionId = transactionId;
		this.transactionDate = transactionDate;
		this.type = type;
		this.from = from;
		this.to = to;
		this.amount = amount;
		this.description = description;
	}
}
