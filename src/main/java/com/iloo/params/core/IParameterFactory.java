package com.iloo.params.core;

/**
 * Interface factory for creating parameter categories and items.
 */
public sealed interface IParameterFactory permits ParameterFactory {
	/**
	 * Creates a new parameter category with the given label and description.
	 *
	 * @param label       the label for this category.
	 * @param description the description for this category.
	 * @return a new parameter category.
	 */
	ParameterCategory createParameterCategory(String label, String description);

	/**
	 * Creates a new parameter item with the given label, value, category, and
	 * active status.
	 *
	 * @param label  the label for this item.
	 * @param value  the value for this item.
	 * @param active {@code true} if this item is active, {@code false} otherwise.
	 * @param <T>    the type of the value stored in this item.
	 * @return a new parameter item.
	 */
	<T> ParameterItem<T> createParameterItem(String label, T value, boolean active);
}
