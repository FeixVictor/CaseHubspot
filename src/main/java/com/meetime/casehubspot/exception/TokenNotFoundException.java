package com.meetime.casehubspot.exception;

public class TokenNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
    public TokenNotFoundException(String message) {
        super(message);
    }
    
}
