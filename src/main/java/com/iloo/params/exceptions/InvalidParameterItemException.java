package com.iloo.params.exceptions;

public class InvalidParameterItemException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception with the specified detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval by the
	 *                getMessage() method).
	 */
	public InvalidParameterItemException(String message) {
		super(message);
	}

	/**
	 * Constructs an exception indicating that the specified value type is invalid
	 * for the parameter item class.
	 *
	 * @return an InvalidParameterItemException with a message indicating an invalid
	 *         value type.
	 */
	public static InvalidParameterItemException forInvalidValueType() {
		return new InvalidParameterItemException("Value must be of type String, Number, Date, or Path.");
	}

}
