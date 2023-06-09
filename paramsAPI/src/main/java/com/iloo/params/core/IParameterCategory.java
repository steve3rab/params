package com.iloo.params.core;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.jdt.annotation.NonNull;

import com.iloo.params.utils.VoidResult;

/**
 *
 * This interface represents a parameter category, which can contain parameter
 * items and other categories.
 *
 * It provides methods for getting and setting information about the category
 * and its contents, as well as methods for navigating the category hierarchy.
 */
public interface IParameterCategory {

	/**
	 *
	 * Returns the label of the category.
	 *
	 * @return the label of the category
	 */
	String getLabel();

	/**
	 *
	 * Returns the description of the category.
	 *
	 * @return the description of the category
	 */
	String getDescription();

	/**
	 *
	 * Adds a parameter item to the category.
	 *
	 * @param parameterItem the parameter item to add
	 * @return {@code VoidResult}
	 */
	VoidResult addParameterItem(@NonNull IParameterItem<?> parameterItem);

	/**
	 *
	 * Removes a parameter item from the category.
	 *
	 * @param parameterItem the parameter item to remove
	 * @return {@code VoidResult}
	 */
	VoidResult removeParameterItem(@NonNull IParameterItem<?> parameterItem);

	/**
	 *
	 * Returns a map of all parameter items in the category, keyed by their names.
	 *
	 * @return a map of parameter items in the category
	 */
	Map<String, IParameterItem<?>> getParameterItems();

	/**
	 *
	 * Returns an optional containing the parent category of this category, or an
	 * empty optional if this category is the root category.
	 *
	 * @return an optional containing the parent category, or an empty optional if
	 *         this category is the root category
	 */
	Optional<IParameterCategory> getParentCategory();

	/**
	 *
	 * Sets the child category of this category.
	 *
	 * @param childCategory the child category to set
	 * @return {@code VoidResult}
	 */
	VoidResult setChildCategory(@NonNull IParameterCategory childCategory);

	/**
	 *
	 * Sets the parent category of this category.
	 *
	 * @param parentCategory the parent category to set
	 * @return {@code VoidResult}
	 */
	VoidResult setParentCategory(@NonNull IParameterCategory parentCategory);

	/**
	 *
	 * Returns true if this category is the root category, false otherwise.
	 *
	 * @return true if this category is the root category, false otherwise
	 */
	boolean isRoot();

	/**
	 *
	 * Returns true if this category is not the root category and has no child,
	 * false otherwise.
	 *
	 * @return true if this category is not the root category and has no child,
	 *         false otherwise
	 */
	boolean isLeaf();

	/**
	 * Returns {@code true} if this has the same parent category, {@code false}
	 * otherwise.
	 *
	 * @param parameterCategory the other parameter category to check
	 *
	 * @return {@code true} if this has the same parent category, {@code false}
	 *         otherwise.
	 */
	boolean areSiblings(@NonNull IParameterCategory parameterCategory);

	/**
	 *
	 * Returns the level of this category in the hierarchy.
	 *
	 * @return the level of this category in the hierarchy
	 */
	ParamaterLevel getLevel();

	/**
	 *
	 * Returns a list of all parent categories of this category, in order from the
	 * root category to the immediate parent of this category.
	 *
	 * @return a list of all parent categories of this category
	 */
	List<IParameterCategory> getAllParentCategories();

	/**
	 *
	 * Returns a list of all child categories of this category.
	 *
	 * @return a list of all child categories of this category
	 */
	List<IParameterCategory> getChildCategoryList();

	/**
	 *
	 * Returns a map of all parameter items in all parent categories of this
	 * category, keyed by their names.
	 *
	 * @return a map of all parameter items in all parent categories of this
	 *         category
	 */
	Map<String, IParameterItem<?>> getAllParentParameterItems();

	/**
	 * Performs a Depth-First Search (DFS) traversal on the category and its
	 * descendants, applying the provided predicate to filter the ParameterCategory
	 * items, and returns a list of the matching items.
	 *
	 * @param predicate the predicate to filter the ParameterCategory items.
	 * @return a list of ParameterCategory items that match the predicate.
	 */
	List<IParameterCategory> depthFirstSearch(Predicate<IParameterCategory> predicate);

	/**
	 * Performs a Breadth-First Search (BFS) traversal on the category and its
	 * descendants, applying the provided predicate to filter the ParameterCategory
	 * items, and returns a list of the matching items.
	 *
	 * @param predicate the predicate to filter the ParameterCategory items.
	 * @return a list of ParameterCategory items that match the predicate.
	 */
	List<IParameterCategory> breadthFirstSearch(Predicate<IParameterCategory> predicate);
}
