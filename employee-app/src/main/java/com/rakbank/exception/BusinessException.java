package com.rakbank.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 6101910523638073462L;
	private String message;

	public BusinessException(String message) {
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
