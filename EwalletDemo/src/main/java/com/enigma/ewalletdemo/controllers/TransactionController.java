package com.enigma.ewalletdemo.controllers;

import com.enigma.ewalletdemo.exeptions.CustomErrorResponse;
import com.enigma.ewalletdemo.responses.*;
import com.enigma.ewalletdemo.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Validated
@RestController
@EnableAutoConfiguration
@RequestMapping("transaction")
public class TransactionController {
	
	@Autowired
	private TransactionService services;
	
	@GetMapping("/search")
	public List<TransactionDto> getListTransactionByAccountNumberAndDate(@RequestParam String accountNumber, @RequestParam Date date)
	{
		return services.getListTransactionByAccountNumberAndDate(accountNumber, date);
	}
	
	@PostMapping("/balance")
	public CustomErrorResponse postBalance(@Valid @RequestBody TransactionBalanceDto request,
										   UriComponentsBuilder builder, HttpServletRequest req) {
		services.postBalance(request);
		String path = req.getRequestURI();
		CustomErrorResponse message = new CustomErrorResponse(LocalDateTime.now(), HttpStatus.OK.value(), "add balance success", path);
		return message;
	}
	
	@PostMapping("/transfer")
	public CustomErrorResponse postTransfer(@Valid @RequestBody TransactionTransferDto request,
			UriComponentsBuilder builder, HttpServletRequest req) {
		services.postTransfer(request);
		String path = req.getRequestURI();
		CustomErrorResponse message = new CustomErrorResponse(LocalDateTime.now(), HttpStatus.OK.value(), "tranfer from "+request.getAccountNo()+" to "+request.getTo()+" success", path);
		return message;
	}
	
	@PostMapping("/point")
	public CustomErrorResponse postPoint(@Valid @RequestBody TransactionPointDto request,
			UriComponentsBuilder builder, HttpServletRequest req) {
		String message = services.postPoint(request);
		String path = req.getRequestURI();
		CustomErrorResponse response = new CustomErrorResponse(LocalDateTime.now(), HttpStatus.OK.value(), message, path);
		return response;
	}
	
	@PostMapping("/pulsa")
	public CustomErrorResponse postPulsa(@Valid @RequestBody TransactionPulsaDto request,
			UriComponentsBuilder builder, HttpServletRequest req) {
		String message = services.postPulsa(request);
		String path = req.getRequestURI();
		CustomErrorResponse response = new CustomErrorResponse(LocalDateTime.now(), HttpStatus.OK.value(), message, path);
		return response;
	}
}
