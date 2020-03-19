package com.enigma.ewalletdemo.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionTransferDto {
	@NotEmpty(message = "Account may not be empty")
	@Size(min = 6, max =6, message = "Account must be 6 length") 
	private String accountNo;
	
	@NotEmpty(message = "Account to may not be empty")
	@Size(min = 6, max =6, message = "Account must be 6 length") 
	private String to;
	
	@NotNull(message = "Ammount may not be empty")
	private BigDecimal ammount;
	
	@NotEmpty(message = "Description may not be empty")
	private String description;
}
