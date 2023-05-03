# Params

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
