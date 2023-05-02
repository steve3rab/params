package com.iloo.params.core;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.jdt.annotation.NonNull;

public interface IParameterCategory {

	String getLabel();

	String getDescription();

	void addParameterItem(@NonNull IParameterItem<?> parameterItem);

	void removeParameterItem(@NonNull IParameterItem<?> parameterItem);

	Map<String, IParameterItem<?>> getParameterItems();

	Optional<IParameterCategory> getParentCategory();

	void setChildCategory(@NonNull IParameterCategory childCategory);

	void setParentCategory(@NonNull IParameterCategory parentCategory);

	boolean isRoot();

	ParamaterLevel getLevel();

	List<IParameterCategory> getAllParentCategories();

	Map<String, IParameterItem<?>> getAllParentParameterItems();
}
