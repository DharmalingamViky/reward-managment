package com.stellar.database.exception;

/**
 * This exception is to report that an object was not found in a database operation. 
 *
 */
public class ObjectNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String message) {
		super(message);
	}
	
	public ObjectNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
