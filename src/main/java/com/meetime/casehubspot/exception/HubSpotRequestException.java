package com.meetime.casehubspot.exception;

public class HubSpotRequestException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
    public HubSpotRequestException(String message) {
        super(message);
    }

    public HubSpotRequestException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
