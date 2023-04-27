package com.iloo.params.core;

/**
 * Concrete implementation of AParameterFactory that creates instances of
 * ParameterCategory and ParameterItem using their default constructors.
 */
public class ParameterFactory extends AParameterFactory {

	@Override
	public ParameterCategory createParameterCategory(String label, String description) {
		return new ParameterCategory(label, description);
	}

	@Override
	public <T> ParameterItem<T> createParameterItem(String label, T value, boolean active) {
		return new ParameterItem<>(label, value, active);
	}
}
