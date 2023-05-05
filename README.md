# Params

[![Maven Central](https://img.shields.io/maven-central/v/com.iloo/params.svg)(https://search.maven.org/artifact/com.iloo/params/1.0/jar)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/{username}/params/blob/master/LICENSE)

DESCRIPTION
===========

The `Params` framework offers a powerful toolset for developers to effectively manage and organize application parameters. By providing a clear and structured way of creating and organizing parameter categories and items, it helps developers to better manage and configure their applications, resulting in more efficient and streamlined workflows.

Using the IParameterFactory interface, developers can abstract away the details of parameter creation, allowing for a more flexible and maintainable codebase. Additionally, the IParameterCategory and IParameterItem interfaces offer a wide range of methods for accessing and modifying parameter information, making it easy to customize the behavior of the application to meet specific requirements.

By adopting the `Params` framework, developers can ensure that their applications are easy to configure and maintain, resulting in a more robust and scalable product. Whether you're building a simple web application or a complex enterprise system, `Params` offers a powerful set of tools for managing and organizing application parameters, making it a must-have tool for developers looking to streamline their workflows and improve the overall quality of their code.

IParameterFactory Interface
===========================

The `IParameterFactory` interface is a useful way to abstract away the details of creating parameter categories and items. By using this interface, you can create code that is more flexible and easier to maintain.

Methods
-------

### `createParameterCategory(String label, String description)`

Creates a new parameter category with the given label and description.

*   `label` - The label for this category.
*   `description` - The description for this category.
*   Returns - A new `IParameterCategory`.

### `createParameterItem(String label, T value, boolean active)`

Creates a new parameter item with the given label, value, category, and active status.

*   `label` - The label for this item.
*   `value` - The value for this item.
*   `active` - A boolean indicating whether this item is active or not.
*   Returns - A new `IParameterItem<T>`, where `T` is the type of the value stored in this item.

The `IParameterFactory` interface is a useful way to abstract away the details of creating parameter categories and items. By using this interface, you can write code that is decoupled from the specific implementation of the factory class.

`IParameterItem` Interface Methods
--------------------------------------

#### `getLabel()`

The getLabel() method returns the label of the parameter item.

Example: `item.getLabel(); // returns "General Settings"`

#### `getValue()`

The getValue() method returns the value of the parameter item.

Example: `item.getValue(); // returns "General Settings"`

#### `setActive(boolean active)`
The setActive(boolean active) method sets the active state of the parameter item.

Example: `item.setActive(true); // set "General Settings"`

#### `isActive()`
The isActive() method returns the active state of the parameter item. It returns true if the parameter item is active, and false otherwise.

Example: `item.isActive(); // asks "General Settings"`

`IParameterCategory` Interface Methods
--------------------------------------

#### `getLabel()`

Returns the **label** of the category.

Example: `category.getLabel(); // returns "General Settings"`

#### `getDescription()`

Returns the **description** of the category.

Example: `category.getDescription(); // returns "Settings that affect the behavior of the application as a whole"`

#### `addParameterItem(IParameterItem<?> parameterItem)`

Adds a **parameter item** to the category.

Example: `category.addParameterItem(new StringParameter("username", "Username", "The username of the user"));`

#### `removeParameterItem(IParameterItem<?> parameterItem)`

Removes a **parameter item** from the category.

Example: `category.removeParameterItem(usernameParameter);`

#### `getParameterItems()`

Returns a **map of all parameter items** in the category, keyed by their names.

Example: `Map<String, IParameterItem<?>> parameterItems = category.getParameterItems();`

#### `getParentCategory()`

Returns an **optional** containing the **parent category** of this category, or an empty optional if this category is the root category.

Example: `Optional<IParameterCategory> parentCategory = category.getParentCategory();`

#### `setChildCategory(IParameterCategory childCategory)`

Sets the **child category** of this category.

Example: `category.setChildCategory(new ParameterCategory("Advanced Settings", "Settings for advanced users"));`

#### `setParentCategory(IParameterCategory parentCategory)`

Sets the **parent category** of this category.

Example: `category.setParentCategory(parentCategory);`

#### `isRoot()`

Returns **true** if this category is the root category, **false** otherwise.

Example: `boolean isRoot = category.isRoot();`

#### `isLeaf()`

Returns **true** if this category is not the root category and has no child, **false** otherwise.

Example: `boolean isLeaf = category.isLeaf();`

#### `areSiblings(IParameterCategory parameterCategory)`

Returns **true** if this category has the same parent category, **false** otherwise.

Example: `boolean areSiblings = category.areSiblings(parameterCategory);`

#### `getLevel()`

Returns the **level** of this category in the hierarchy.

Example: `ParamaterLevel level = category.getLevel();`

#### `getAllParentCategories()`

Returns a **list of all parent categories** of this category, in order from the root category to the immediate parent of this category.

Example: `List<IParameterCategory> parentCategories = category.getAllParentCategories();`

#### `getChildCategoryList()`

Returns a **list of child categories**

Example: `List<IParameterCategory> childCategories = category.getChildCategoryList();`

#### `getAllParentParameterItems()`

Returns a **map of all parameter items** in this category and all its parent categories, keyed by their names.

Example: `Map<String, IParameterItem<?>> allParameterItems = category.getAllParentParameterItems();`
