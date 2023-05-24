package com.iloo.params.core;

import java.nio.file.Path;
import java.util.Date;
import java.util.EnumSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.iloo.params.exceptions.InvalidParameterItemException;

/**
 * Validator class to validate the type of the value for ParameterItem.
 */
class ParameterItemValueValidator {

	/**
	 * Enum defining the valid value types.
	 */
	enum ValueType {
		/**
		 * Value type representing a String.
		 */
		STRING(String.class),

		/**
		 * Value type representing a Number.
		 */
		NUMBER(Number.class),

		/**
		 * Value type representing a Date.
		 */
		DATE(Date.class),

		/**
		 * Value type representing a Path.
		 */
		PATH(Path.class),

		/**
		 * Value type representing a Consumer.
		 */
		CONSUMER(Consumer.class),

		/**
		 * Value type representing a Supplier.
		 */
		SUPPLIER(Supplier.class),

		/**
		 * Value type representing a Function.
		 */
		FUNCTION(Function.class);

		/**
		 * Value type reference representing a class.
		 */
		private final Class<?> valueTypeRef;

		/**
		 * Constructs a ValueType enum constant with the specified value type.
		 *
		 * @param valueType the Class representing the value type.
		 */
		ValueType(Class<?> valueType) {
			this.valueTypeRef = valueType;
		}

		/**
		 * Returns the Class representing the value type.
		 *
		 * @return the Class representing the value type.
		 */
		public Class<?> getValueType() {
			return valueTypeRef;
		}
	}

	/**
	 * Set of valid types.
	 */
	private static final EnumSet<ValueType> VALID_TYPES = EnumSet.allOf(ValueType.class);

	/**
	 * ParameterItemValueValidator constructor.
	 */
	private ParameterItemValueValidator() {
		// Hide implicit constructor
	}

	/**
	 * Validates that the given value is of a valid type.
	 *
	 * @param <T>   the type of the value.
	 * @param value the value to validate.
	 * @return the validated value.
	 * @throws InvalidParameterItemException if the value is not of a valid type.
	 */
	static <T> T validateValueType(T value) {
		Class<?> valueType = value.getClass();
		if (!isValidType(valueType)) {
			throw InvalidParameterItemException.forInvalidValueType();
		}
		return value;
	}

	private static boolean isValidType(Class<?> valueType) {
		for (ValueType validType : VALID_TYPES) {
			if (validType.getValueType().isAssignableFrom(valueType)) {
				return true;
			}
		}
		return false;
	}
}
