package com.iloo.params.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a category of parameters.
 */
public class ParameterCategory {
	private final String label;
	private final String description;
	private final Map<String, ParameterItem<?>> parameterItems;
	private ParameterCategory parentCategory;

	/**
	 * Creates a new parameter category with the given label and description.
	 *
	 * @param label       the label for this category.
	 * @param description the description for this category.
	 */
	ParameterCategory(String label, String description) {
		this.label = Objects.requireNonNull(label, "Label cannot be null");
		this.description = Objects.requireNonNull(description, "Description cannot be null");
		parameterItems = new HashMap<>();
	}

	/**
	 * Returns the label for this category.
	 *
	 * @return the label for this category.
	 */
	public String getLabel() {
		return new String(label);
	}

	/**
	 * Returns the description for this category.
	 *
	 * @return the description for this category.
	 */
	public String getDescription() {
		return new String(description);
	}

	/**
	 * Returns a map of the parameter items belonging to this category.
	 *
	 * @return a map of the parameter items belonging to this category.
	 */
	public Map<String, ParameterItem<?>> getParameterItems() {
		return Collections.unmodifiableMap(parameterItems);
	}

	/**
	 * Adds a parameter item to this category.
	 *
	 * @param parameterItem the parameter item to put.
	 * @throws NullPointerException if the parameter item is {@code null}.
	 */
	public void addParameterItem(ParameterItem<?> parameterItem) {
		Objects.requireNonNull(parameterItem, "Parameter item cannot be null");

		parameterItems.computeIfPresent(parameterItem.getLabel(), (label, existingItem) -> {
			throw new IllegalArgumentException("Parameter item already exists in the category");
		});

		parameterItems.put(parameterItem.getLabel(), parameterItem);
		parameterItem.getCategory().setParentCategory(this);
	}

	/**
	 * Returns the parent category of this category, or {@code null} if this
	 * category has no parent.
	 *
	 * @return the parent category of this category, or {@code null} if this
	 *         category has no parent.
	 */
	public ParameterCategory getParentCategory() {
		return parentCategory;
	}

	/**
	 * Sets the parent category of this category.
	 *
	 * @param parentCategory the parent category of this category.
	 */
	public void setParentCategory(ParameterCategory parentCategory) {
		this.parentCategory = parentCategory;
	}

	/**
	 * Returns {@code true} if this category has no parent category, {@code false}
	 * otherwise.
	 *
	 * @return {@code true} if this category has no parent category, {@code false}
	 *         otherwise.
	 */
	public boolean isRoot() {
		return parentCategory == null;
	}

	/**
	 * Returns a list of parent categories
	 *
	 * @return list of parent categories
	 */
	public List<ParameterCategory> getAllParentCategories() {
		return Stream.iterate(getParentCategory(), Objects::nonNull, ParameterCategory::getParentCategory)
				.collect(Collectors.toList());
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, label, parameterItems, parentCategory);
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
		ParameterCategory other = (ParameterCategory) obj;
		return Objects.equals(description, other.description) && Objects.equals(label, other.label)
				&& Objects.equals(parameterItems, other.parameterItems)
				&& Objects.equals(parentCategory, other.parentCategory);
	}

}
