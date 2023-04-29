package com.iloo.params.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import com.iloo.params.exceptions.InvalidParameterCategoryException;

/**
 * Represents a category of parameters.
 */
class ParameterCategory implements IParameterCategory {
	private final String label;
	private final String description;
	private final Map<String, IParameterItem<?>> parameterItems;
	private Optional<IParameterCategory> parentCategory = Optional.empty();

	/**
	 * Creates a new parameter category with the given label and description.
	 *
	 * @param label       the label for this category.
	 * @param description the description for this category.
	 * @throws NullPointerException if the label or description {@code null}.
	 */
	ParameterCategory(@NonNull String label, @NonNull String description) {
		this.label = Objects.requireNonNull(label, "Label cannot be null");
		this.description = Objects.requireNonNull(description, "Description cannot be null");
		parameterItems = new ConcurrentHashMap<>();
	}

	/**
	 * Adds a parameter item to this category.
	 *
	 * @param parameterItem the parameter item to put.
	 * @throws NullPointerException if the parameter item is {@code null}.
	 */
	@Override
	public synchronized void addParameterItem(@NonNull IParameterItem<?> parameterItem) {
		Objects.requireNonNull(parameterItem, "Parameter item cannot be null");

		parameterItems.computeIfPresent(parameterItem.getLabel(), (lbl, existingItem) -> {
			throw InvalidParameterCategoryException.forInvalidParameterItem(parameterItem,
					"Parameter item already exists in the category");
		});

		parameterItems.put(parameterItem.getLabel(), parameterItem);
	}

	/**
	 * Remove a parameter item to this category.
	 *
	 * @param parameterItem the parameter item to put.
	 * @throws NullPointerException if the parameter item is {@code null}.
	 */
	@Override
	public synchronized void removeParameterItem(@NonNull IParameterItem<?> parameterItem) {
		Objects.requireNonNull(parameterItem, "Parameter item cannot be null");
		parameterItems.remove(parameterItem.getLabel(), parameterItem);
	}

	/**
	 * Returns the label for this category.
	 *
	 * @return the label for this category.
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/**
	 * Returns the description for this category.
	 *
	 * @return the description for this category.
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * Returns a map of the parameter items belonging to this category.
	 *
	 * @return a map of the parameter items belonging to this category.
	 */
	@Override
	public Map<String, IParameterItem<?>> getParameterItems() {
		return Collections.unmodifiableMap(parameterItems);
	}

	/**
	 * Returns the optional parent category of this category, or
	 * {@code Optional#empty()} if this category has no parent.
	 *
	 * @return the optional parent category of this category, or
	 *         {@code Optional#empty()} if this category has no parent.
	 */
	@Override
	public Optional<IParameterCategory> getParentCategory() {
		return parentCategory;
	}

	/**
	 * Sets the parent category of this category.
	 *
	 * @param parentCategory the parent category of this category.
	 * @throws NullPointerException if {@link ParameterCategory} is {@code null}.
	 */
	@Override
	public void setParentCategory(@NonNull IParameterCategory parentCategory) {
		Objects.requireNonNull(parentCategory, "Parameter category cannot be null");

		// Check if it takes itself as parent
		if (parentCategory == this) {
			throw InvalidParameterCategoryException.forCircularDependency();
		}

		// Check if the parent category has the same label as any of this category's
		// ancestors
		IParameterCategory ancestor = parentCategory;
		while (ancestor != null) {
			if (label.equals(ancestor.getLabel())) {
				throw InvalidParameterCategoryException.forInvalidLabelValue(label,
						"Child category cannot have the same label as any of its parent categories");
			}
			ancestor = ancestor.getParentCategory().orElse(null);
		}

		this.parentCategory = Optional.of(parentCategory);
	}

	/**
	 * Returns {@code true} if this category has no parent category, {@code false}
	 * otherwise.
	 *
	 * @return {@code true} if this category has no parent category, {@code false}
	 *         otherwise.
	 */
	@Override
	public boolean isRoot() {
		return parentCategory.isEmpty();
	}

	/**
	 * Returns a list of parent categories
	 *
	 * @return list of parent categories
	 */
	@Override
	public List<IParameterCategory> getAllParentCategories() {
		return Stream
				.iterate(getParentCategory(), parentCgy -> parentCgy.flatMap(IParameterCategory::getParentCategory))
				.takeWhile(Optional::isPresent).map(Optional::get).toList();
	}

	/**
	 * Returns a map of all parameter items in this category and its parent
	 * categories.
	 *
	 * @return a map of all parameter items in this category and its parent
	 *         categories.
	 */
	@Override
	public Map<String, IParameterItem<?>> getAllParentParameterItems() {
		Map<String, IParameterItem<?>> allParameterItems = new HashMap<>();
		parentCategory.ifPresent(parent -> allParameterItems.putAll(parent.getAllParentParameterItems()));
		allParameterItems.putAll(parameterItems);
		return allParameterItems.entrySet().stream().collect(
				Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue, (existingItem, newItem) -> newItem));
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
		return Objects.deepEquals(description, other.description) && Objects.deepEquals(label, other.label)
				&& Objects.deepEquals(parameterItems, other.parameterItems)
				&& Objects.deepEquals(parentCategory, other.parentCategory);
	}

}
