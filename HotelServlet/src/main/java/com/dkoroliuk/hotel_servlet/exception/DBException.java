package com.dkoroliuk.hotel_servlet.exception;

/**
 * Class for data base exceptions
 */
public class DBException extends Exception {
	private static final long serialVersionUID = 1L;

	public DBException(String message, Throwable cause) {
		super(message, cause);
	}

}
