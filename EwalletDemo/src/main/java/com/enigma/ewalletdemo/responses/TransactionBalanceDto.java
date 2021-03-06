package com.enigma.ewalletdemo.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionBalanceDto {

	@NotEmpty(message = "Account may not be empty")
	@Size(min = 6, max =6, message = "Account must be 6 length") 
	private String accountNo;
	
	@NotNull(message = "Ammount may not be empty")
	private BigDecimal ammount;
}
