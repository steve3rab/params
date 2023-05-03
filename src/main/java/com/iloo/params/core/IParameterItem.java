package com.iloo.params.core;

/**
 * 
 * This interface represents a parameter item that has a label, value, and
 * active state.
 * 
 * @param <T> the type of the parameter value
 */
public interface IParameterItem<T> {

	/**
	 * 
	 * Returns the label of the parameter item.
	 * 
	 * @return the label of the parameter item
	 */
	String getLabel();

	/**
	 * 
	 * Returns the value of the parameter item.
	 * 
	 * @return the value of the parameter item
	 */
	T getValue();

	/**
	 * 
	 * Sets the active state of the parameter item.
	 * 
	 * @param active the active state to set
	 */
	void setActive(boolean active);

	/**
	 * 
	 * Returns the active state of the parameter item.
	 * 
	 * @return {@code true} if the parameter item is active, {@code false} otherwise
	 */
	boolean isActive();
}
