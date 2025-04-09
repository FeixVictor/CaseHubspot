package com.meetime.casehubspot.exception;

public class OAuthException extends RuntimeException {
	
    private static final long serialVersionUID = 1L;

    public OAuthException(String message) {
        super(message);
    }

    public OAuthException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
