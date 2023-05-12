package com.iloo.params.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Represents a list of parameter categories.
 */
class ParameterCategoryList implements IParameterCategoryList {

	/**
	 * A list of {@link IParameterCategory}.
	 */
	private List<IParameterCategory> categoryList;

	/**
	 * Constructs a new ParameterCategoryList with an empty category list.
	 */
	ParameterCategoryList() {
		this.categoryList = new ArrayList<>();
	}

	/**
	 * Adds a parameter category to the list.
	 *
	 * @param parameterCategory the parameter category to add
	 */
	void addParameterCategory(@NonNull IParameterCategory parameterCategory) {
		categoryList.add(parameterCategory);
	}

	/**
	 * Returns the list of parameter categories.
	 *
	 * @return the list of parameter categories
	 */
	@Override
	public List<IParameterCategory> getParameterCategories() {
		return List.copyOf(categoryList);
	}

	/**
	 * Returns the number of parameter categories in the list.
	 *
	 * @return the number of parameter categories
	 */
	@Override
	public int getSize() {
		return categoryList.size();
	}

	/**
	 * Checks if the list is empty.
	 *
	 * @return true if the list is empty, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return categoryList.isEmpty();
	}
}
