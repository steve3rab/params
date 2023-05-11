package com.iloo.params.core;

import java.util.Objects;

/**
 * Concrete implementation of IParameterFactory that creates instances of
 * ParameterCategory and ParameterItem using their default constructors.
 */
public final class ParameterFactory implements IParameterFactory {

	@Override
	public ParameterCategory createParameterCategory(String label, String description) {
		return new ParameterCategory(Objects.requireNonNull(label, "Label cannot be null"),
				Objects.requireNonNull(description, "Description cannot be null"));
	}

	@Override
	public <T> ParameterItem<T> createParameterItem(String label, T value, boolean active) {
		return new ParameterItem<>(Objects.requireNonNull(label, "Label cannot be null"),
				Objects.requireNonNull(value, "Value cannot be null"), active);
	}
}
