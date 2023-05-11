package com.iloo.params.core;

import java.util.Objects;

/**
 * Concrete implementation of IParameterFactory that creates instances of
 * ParameterCategory and ParameterItem using their default constructors.
 */
public final class ParameterFactory implements IParameterFactory {

	/**
	 * A list of {@link IParameterCategory}.
	 */
	final ParameterCategoryList parameterCategoryList = new ParameterCategoryList();

	@Override
	public ParameterCategory createParameterCategory(String label, String description) {
		ParameterCategory parameterCategory = new ParameterCategory(
				Objects.requireNonNull(label, "Label cannot be null"),
				Objects.requireNonNull(description, "Description cannot be null"));
		parameterCategoryList
				.addParameterCategory(Objects.requireNonNull(parameterCategory, "Parameter category cannot be null"));
		return parameterCategory;
	}

	@Override
	public <T> ParameterItem<T> createParameterItem(String label, T value, boolean active) {
		return new ParameterItem<>(Objects.requireNonNull(label, "Label cannot be null"),
				Objects.requireNonNull(value, "Value cannot be null"), active);
	}

	@Override
	public IParameterCategoryList getParameterCategoryList() {
		return parameterCategoryList;
	}
}
