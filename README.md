# Params

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
