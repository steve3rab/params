package com.iloo.params.exceptions;

/**
 * Exception class representing an error in VoidResult.
 */
public class VoidResultException extends Exception {

	private static final long serialVersionUID = 1L;

	public VoidResultException(String message) {
		super(message);
	}

	public VoidResultException(String message, Throwable cause) {
		super(message, cause);
	}
}
