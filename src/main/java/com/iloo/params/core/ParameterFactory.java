package com.iloo.params.core;

/**
 * Concrete implementation of IParameterFactory that creates instances of
 * ParameterCategory and ParameterItem using their default constructors.
 */
public final class ParameterFactory implements IParameterFactory {

	@Override
	public ParameterCategory createParameterCategory(String label, String description) {
		return new ParameterCategory(label, description);
	}

	@Override
	public <T> ParameterItem<T> createParameterItem(String label, T value, boolean active) {
		return new ParameterItem<>(label, value, active);
	}
}
