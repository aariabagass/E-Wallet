package com.enigma.ewalletdemo.exeptions;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


public class ErrorDetails {
	@ApiModelProperty(notes = "timestamp of error")
	private Date timestamp;
	@ApiModelProperty(notes = "message of error")
	private String message;
	
	@ApiModelProperty(notes = "detail of error")
	private String details;

	public ErrorDetails(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

}
