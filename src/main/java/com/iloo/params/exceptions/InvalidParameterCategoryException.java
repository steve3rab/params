package com.iloo.params.exceptions;

import com.iloo.params.core.IParameterItem;

/**
 * Exception thrown when a parameter is invalid or has a conflict.
 */
public class InvalidParameterCategoryException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception with the specified detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval by the
	 *                getMessage() method).
	 */
	public InvalidParameterCategoryException(String message) {
		super(message);
	}

	/**
	 * Constructs an exception indicating that a circular dependency was detected
	 * for a parameter with the specified label.
	 *
	 * @return an InvalidParameterCategoryException with a message indicating a
	 *         circular dependency.
	 */
	public static InvalidParameterCategoryException forCircularDependency() {
		return new InvalidParameterCategoryException("Circular dependency detected for parameter");
	}

	/**
	 * Constructs an exception indicating that the specified value is invalid for
	 * the parameter with the specified label.
	 *
	 * @param parameterLabel the label of the parameter with the invalid value.
	 * @param message        a detailed message describing the invalid value.
	 * @return an InvalidParameterCategoryException with a message indicating an
	 *         invalid value.
	 */
	public static InvalidParameterCategoryException forInvalidLabelValue(String parameterLabel, String message) {
		return new InvalidParameterCategoryException(
				"Invalid value for parameter '" + parameterLabel + "': " + message);
	}

	/**
	 * Constructs an exception indicating that the specified {@link ParameterItem}
	 * is invalid.
	 *
	 * @param parameterItem the parameter item.
	 * @param message       a detailed message describing the invalid value.
	 * @return an InvalidParameterCategoryException with a message indicating an
	 *         invalid value.
	 */
	public static InvalidParameterCategoryException forInvalidParameterItem(IParameterItem<?> parameterItem,
			String message) {
		return new InvalidParameterCategoryException("Invalid value for parameter '" + parameterItem + "': " + message);
	}
}
