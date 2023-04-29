package com.iloo.params.core;

public interface IParameterItem<T> {

	String getLabel();

	T getValue();

	void setActive(boolean active);

	boolean isActive();
}
