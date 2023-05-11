package com.iloo.params.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import com.iloo.params.exceptions.InvalidParameterCategoryException;

/**
 * Represents a category of parameters.
 */
class ParameterCategory implements IParameterCategory {
	/**
	 * The label for this category.
	 */
	private final String label;

	/**
	 * The description for this category.
	 */
	private final String description;

	/**
	 * The {@link IParameterItem} for this category.
	 */
	private final Map<String, IParameterItem<?>> parameterItems;

	/**
	 * The {@link ParamaterLevel} for this category.
	 */
	private final ParamaterLevel level;

	/**
	 * The parent of this category.
	 */
	private Optional<IParameterCategory> parentCategoryOp = Optional.empty();

	/**
	 * The descendant of this category.
	 */
	private final List<IParameterCategory> childCategoryList;

	/**
	 * Creates a new parameter category with the given label and description.
	 *
	 * @param label       the label for this category.
	 * @param description the description for this category.
	 * @throws NullPointerException if the label or description {@code null}.
	 */
	ParameterCategory(@NonNull String label, @NonNull String description) {
		this.label = label;
		this.description = description;
		this.parameterItems = new ConcurrentHashMap<>();
		this.childCategoryList = new CopyOnWriteArrayList<>();
		this.level = new ParamaterLevel();
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
		return parentCategoryOp;
	}

	/**
	 * Sets the child category of this category.
	 *
	 * @param childCategory the child category of this category.
	 */
	@Override
	public void setChildCategory(@NonNull IParameterCategory childCategory) {
		childCategory.setParentCategory(this);
		this.childCategoryList.add(childCategory);
	}

	/**
	 * Sets the parent category of this category.
	 *
	 * @param parentCategory the parent category of this category.
	 * @throws NullPointerException if {@link ParameterCategory} is {@code null}.
	 */
	@Override
	public void setParentCategory(@NonNull IParameterCategory parentCategory) {
		setSubCategory(parentCategory);
		ParameterCategory parameterCategory = (ParameterCategory) parentCategory;
		parameterCategory.getDirectChildCategoryList().add(this);
	}

	private void setSubCategory(IParameterCategory subCategory) {
		Objects.requireNonNull(subCategory, "Parameter category cannot be null");

		// Check if it takes itself as parent
		if (subCategory == this) {
			throw InvalidParameterCategoryException.forCircularDependency();
		}

		// Check if the parent category has the same label as any of this category's
		// ancestors
		IParameterCategory ancestor = subCategory;
		while (ancestor != null) {
			if (label.equals(ancestor.getLabel())) {
				throw InvalidParameterCategoryException.forInvalidLabelValue(label,
						"Child category cannot have the same label as any of its parent categories");
			}
			ancestor = ancestor.getParentCategory().orElse(null);
		}

		this.parentCategoryOp = Optional.of(subCategory);
		ParamaterLevel parentLevel = subCategory.getLevel();
		parentLevel.incrementHorizontal();
		level.setVertical(parentLevel.getVertical() + 1);
	}

	/**
	 * Returns the level of the category in the hierarchy.
	 *
	 * @return the level of the category in the hierarchy.
	 */
	@Override
	public ParamaterLevel getLevel() {
		return level;
	}

	/**
	 * Returns a list of direct category children.
	 *
	 * @return a list of direct category children.
	 */
	private List<IParameterCategory> getDirectChildCategoryList() {
		return childCategoryList;
	}

	/**
	 * Returns a list of category children.
	 *
	 * @return a list of category children.
	 */
	@Override
	public List<IParameterCategory> getChildCategoryList() {
		return List.copyOf(childCategoryList);
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
		return parentCategoryOp.isEmpty();
	}

	/**
	 * Returns {@code true} if this category has parent category and no child,
	 * {@code false} otherwise.
	 *
	 * @return {@code true} if this category has parent category and no child,
	 *         {@code false} otherwise.
	 */
	@Override
	public boolean isLeaf() {
		return parentCategoryOp.isPresent() && childCategoryList.isEmpty();
	}

	/**
	 * Returns {@code true} if this has the same parent category, {@code false}
	 * otherwise.
	 *
	 * @param parameterCategory the other parameter category to check
	 *
	 * @return {@code true} if this has the same parent category, {@code false}
	 *         otherwise.
	 */
	@Override
	public boolean areSiblings(@NonNull IParameterCategory parameterCategory) {
		Objects.requireNonNull(parameterCategory, "Parameter category cannot be null");

		Optional<IParameterCategory> otherParent = parameterCategory.getParentCategory();
		return parentCategoryOp.isPresent() && otherParent.isPresent()
				&& parentCategoryOp.get().equals(otherParent.get());
	}

	/**
	 * Returns a list of parent categories.
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
		parentCategoryOp.ifPresent(parent -> allParameterItems.putAll(parent.getAllParentParameterItems()));
		allParameterItems.putAll(parameterItems);
		return allParameterItems.entrySet().stream().collect(
				Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue, (existingItem, newItem) -> newItem));
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, label, parameterItems, parentCategoryOp);
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
				&& Objects.deepEquals(parentCategoryOp, other.parentCategoryOp);
	}

}
