package com.veeteq.finance.bankdocument.exception;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 4075233438202287917L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}