package com.stellar.database.exception;

/**
 * This exception reports access failures towards a database.
 *
 */
public class AccessFailedException extends Exception {


	private static final long serialVersionUID = 1L;

	public AccessFailedException(String message) {
		super(message);
	}
	
	public AccessFailedException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
