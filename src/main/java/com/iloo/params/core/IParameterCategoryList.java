package com.iloo.params.core;

import java.util.List;

/**
 * This interface represents a list of parameter categories.
 */
public sealed interface IParameterCategoryList permits ParameterCategoryList {

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
