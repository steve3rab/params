package com.iloo.params.core;

import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;

import org.eclipse.jdt.annotation.NonNull;

import com.iloo.params.exceptions.InvalidParameterItemException;

/**
 * Represents an item in a {@link ParameterCategory}.
 *
 * @param <T> the type of the value stored in this item.
 */
public class ParameterItem<T> {
	private final String label;
	private final T value;
	private boolean active;

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
	ParameterItem(@NonNull String label, @NonNull T value, boolean active) {
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
	 * Returns the label for this item.
	 *
	 * @return the label for this item.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Returns the value for this item.
	 *
	 * @return the value for this item.
	 */
	public T getValue() {
		return value;
	}

	/**
	 * Returns {@code true} if this item is active, {@code false} otherwise.
	 *
	 * @return {@code true} if this item is active, {@code false} otherwise.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets the active status of this item.
	 *
	 * @param active {@code true} if this item is active, {@code false} otherwise.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public int hashCode() {
		return Objects.hash(active, label, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ParameterItem<?> other = (ParameterItem<?>) obj;
		return (active == other.active) && Objects.equals(label, other.label) && Objects.equals(value, other.value);
	}

}
