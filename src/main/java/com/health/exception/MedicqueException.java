package com.health.exception;

public class MedicqueException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MedicqueException(String message) {
		super(message);
	}

	public MedicqueException(String message, Throwable cause) {
		super(message, cause);
	}
}
