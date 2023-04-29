package com.iloo.params.core;

import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;

import org.eclipse.jdt.annotation.NonNull;

import com.iloo.params.exceptions.InvalidParameterItemException;

/**
 * Represents a parameter item with a label, value, and active status.
 *
 * @param <T> the type of the value for this parameter item.
 */
public record ParameterItem<T>(@NonNull String label, @NonNull T value, boolean active) {
	/**
	 * Creates a new parameter item with the given label, value, category, and
	 * active status.
	 *
	 * @param label  the label for this item.
	 * @param value  the value for this item.
	 * @param active {@code true} if this item is active, {@code false} otherwise.
	 * @throws NullPointerException          if label or value is {@code null}.
	 * @throws InvalidParameterItemException if the value is not of type String,
	 *                                       Number, Date, or Path.
	 */
	public ParameterItem(@NonNull String label, @NonNull T value, boolean active) {
		this.label = Objects.requireNonNull(label, "Label cannot be null");
		this.value = validateValueType(Objects.requireNonNull(value, "Value cannot be null"));
		this.active = active;
	}

	/**
	 * Validates that the given value is of type String, Number, Date, or Path.
	 *
	 * @param value the value to validate.
	 * @return the value
	 * @throws NullPointerException          if value is {@code null}.
	 * @throws InvalidParameterItemException if the value is not of type String,
	 *                                       Number, Date, or Path.
	 */
	private T validateValueType(@NonNull T value) {
		if (!((value instanceof String) || (value instanceof Number) || (value instanceof Date)
				|| (value instanceof Path))) {
			throw InvalidParameterItemException.forInvalidValueType();
		}
		return value;
	}

	/**
	 * Returns a new {@code ParameterItem} with the same label and value as this
	 * item, but with the given active status.
	 *
	 * @param active {@code true} if the new item should be active, {@code false}
	 *               otherwise.
	 * @return a new {@code ParameterItem} with the same label and value as this
	 *         item, but with the given active status.
	 */
	public ParameterItem<T> withActive(boolean active) {
		return new ParameterItem<>(label, value, active);
	}
}
