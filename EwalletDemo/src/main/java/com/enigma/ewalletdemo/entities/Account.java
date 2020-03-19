package com.enigma.ewalletdemo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "account", length = 500)
	private String accountNo;

	@Column(name = "name", length = 500)
	private String name;

	@Column(name = "balance")
	private BigDecimal balance;

	@Column(name = "point")
	private Double point;

	@Column(name = "status")
	private Integer status;

	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
	private List<Transaction> listTransaction;

	public Account(Long id, String accountNo, String name, BigDecimal balance, Double point, Integer status, List<Transaction> listTransaction) {
		this.id = id;
		this.accountNo = accountNo;
		this.name = name;
		this.balance = balance;
		this.point = point;
		this.status = status;
		this.listTransaction = listTransaction;
	}

	public Account(String accountNo, String name, BigDecimal balance, Double point, Integer status) {
		this.accountNo = accountNo;
		this.name = name;
		this.balance = balance;
		this.point = point;
		this.status = status;
	}
}