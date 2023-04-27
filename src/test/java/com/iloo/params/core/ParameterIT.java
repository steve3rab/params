package com.iloo.params.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ParameterIT {

	private static AParameterFactory factory;

	@BeforeAll
	static void initAll() {
		factory = new ParameterFactory();
	}

	@ParameterizedTest(name = "Create category with label ''{0}'' and description ''{1}''")
	@CsvSource({ "Label 1, Description 1", "Label 2, Description 2", "Label 3, Description 3" })
	@DisplayName("Test a category")
	void testParameterCategory(String label, String description) {
		ParameterCategory category = factory.createParameterCategory(label, description);
		assertEquals(label, category.getLabel());
		assertEquals(description, category.getDescription());
		assertTrue(category.getParameterItems().isEmpty());
		assertNull(category.getParentCategory());
		assertTrue(category.isRoot());
		assertTrue(category.getAllParentCategories().isEmpty());
	}

	@ParameterizedTest(name = "Creating parameter item with label ''{0}'' and value ''{1}''")
	@CsvSource({ "Label 1, Value 1, Label 2, false", "Label 2, Value 2, Label 2, true",
			"Label 3, Value 3, Label 1, false" })
	@DisplayName("Test a parameter item")
	void testParameterItem(String label, String value, String categoryLabel, boolean active) {
		ParameterCategory category = factory.createParameterCategory(categoryLabel, "Test category");
		ParameterItem<String> item = factory.createParameterItem(label, value, category, active);
		assertEquals(label, item.getLabel());
		assertEquals(value, item.getValue());
		assertEquals(category, item.getCategory());
		assertEquals(active, item.isActive());
	}

	@ParameterizedTest(name = "Adding parameter item with label ''{0}'' and value ''{1}''")
	@CsvSource({ "param1, 10, true", "param2, hello, false", "param3, true, true", "param4, 20.5, false" })
	@DisplayName("Test adding parameter items to a category")
	void testAddParameterItem(String label, Object value, boolean active) {
		ParameterCategory category = factory.createParameterCategory("Label 1", "Description 1");
		ParameterItem<Object> item = factory.createParameterItem(label, value, category, active);

		category.addParameterItem(item);

		assertEquals(1, category.getParameterItems().size());
		assertTrue(category.getParameterItems().containsKey(label));
	}

	@ParameterizedTest
	@CsvSource({ "Label 1, Description 1, Label 2, Description 2, Label 3, Description 3",
			"Label 4, Description 4, Label 5, Description 5, Label 6, Description 6" })
	@DisplayName("Test adding parameter items with many categories")
	void testAddParameterItemWithCategories(String label1, String description1, String label2, String description2,
			String label3, String description3) {

		ParameterCategory category1 = factory.createParameterCategory(label1, description1);
		ParameterCategory category2 = factory.createParameterCategory(label2, description2);
		ParameterCategory category3 = factory.createParameterCategory(label3, description3);

		ParameterItem<String> item1 = factory.createParameterItem("Item 1", "Value 1", category1, true);
		ParameterItem<String> item2 = factory.createParameterItem("Item 2", "Value 2", category2, true);
		ParameterItem<String> item3 = factory.createParameterItem("Item 3", "Value 3", category3, true);

		category1.addParameterItem(item1);
		category2.addParameterItem(item2);
		category3.addParameterItem(item3);

		Map<String, ParameterItem<?>> items1 = category1.getParameterItems();
		Map<String, ParameterItem<?>> items2 = category2.getParameterItems();
		Map<String, ParameterItem<?>> items3 = category3.getParameterItems();

		assertTrue(items1.containsKey(item1.getLabel()));
		assertEquals(item1, items1.get(item1.getLabel()));
		assertEquals(category1, item1.getCategory());

		assertTrue(items2.containsKey(item2.getLabel()));
		assertEquals(item2, items2.get(item2.getLabel()));
		assertEquals(category2, item2.getCategory());

		assertTrue(items3.containsKey(item3.getLabel()));
		assertEquals(item3, items3.get(item3.getLabel()));
		assertEquals(category3, item3.getCategory());

		assertThrows(IllegalArgumentException.class, () -> category1.addParameterItem(item1));
		assertThrows(NullPointerException.class, () -> category1.addParameterItem(null));
	}

}
