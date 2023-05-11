package com.iloo.params.core;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

public interface IParameterCategoryList {

	/**
	 * Removes a parameter category from the list.
	 *
	 * @param parameterCategory the parameter category to remove
	 */
	void removeParameterCategory(@NonNull IParameterCategory parameterCategory);

	/**
	 * Returns the list of parameter categories.
	 *
	 * @return the list of parameter categories
	 */
	List<IParameterCategory> getParameterCategories();

	/**
	 * Returns the number of parameter categories in the list.
	 *
	 * @return the number of parameter categories
	 */
	int getSize();

	/**
	 * Checks if the list is empty.
	 *
	 * @return true if the list is empty, false otherwise
	 */
	boolean isEmpty();

}
