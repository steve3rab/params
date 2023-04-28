package com.iloo.params.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.iloo.params.exceptions.InvalidParameterCategoryException;
import com.iloo.params.exceptions.InvalidParameterItemException;

public class ParameterIT {

	private static AParameterFactory factory;

	@BeforeAll
	static void initAll() {
		factory = new ParameterFactory();
	}

	@ParameterizedTest(name = "Create category with label ''{0}'' and description ''{1}''")
	@CsvSource({ "Label 1, Description 1", "Label 2, Description 2" })
	@DisplayName("Test a category")
	void testParameterCategory(String label, String description) {
		ParameterCategory category = factory.createParameterCategory(label, description);
		assertEquals(label, category.getLabel());
		assertEquals(description, category.getDescription());
		assertTrue(category.getParameterItems().isEmpty());
		assertTrue(category.getParentCategory().isEmpty());
		assertTrue(category.isRoot());
		assertTrue(category.getAllParentCategories().isEmpty());
	}

	@ParameterizedTest(name = "Creating parameter item with label ''{0}'' , value ''{1}'' , active ''{3}''")
	@CsvSource({ "Label 1, Value 1, Label 2, false" })
	@DisplayName("Test a parameter item")
	void testParameterItem(String label, String value, String categoryLabel, boolean active) {
		ParameterCategory category = factory.createParameterCategory(categoryLabel, "Test category");
		ParameterItem<String> item = factory.createParameterItem(label, value, active);
		category.addParameterItem(item);
		assertEquals(label, item.getLabel());
		assertEquals(value, item.getValue());
		assertEquals(active, item.isActive());
	}

	@ParameterizedTest(name = "Creating parameter item with label ''{0}'' and active ''{2}''")
	@CsvSource({ "Label 1, false" })
	@DisplayName("Test a parameter item")
	void testParameterItemValueType(String label, boolean active) {
		String valueStr = "Value";
		ParameterItem<String> item1 = factory.createParameterItem(label, valueStr, active);
		assertEquals(label, item1.getLabel());
		assertEquals(valueStr, item1.getValue());
		assertFalse(item1.isActive());

		assertThrows(InvalidParameterItemException.class,
				() -> factory.createParameterItem(label, new File(valueStr), active));

		int valueInt = 2;
		ParameterItem<Integer> item2 = factory.createParameterItem(label, valueInt, active);
		assertSame(valueInt, item2.getValue());
	}

	@ParameterizedTest(name = "Adding and removing parameter item with label ''{0}'' and value ''{1}''")
	@CsvSource({ "param1, 10, true" })
	@DisplayName("Test adding and removing parameter items to a category")
	void testAddRemoveParameterItem(String label, Object value, boolean active) {
		ParameterCategory category = factory.createParameterCategory("Label 1", "Description 1");
		ParameterItem<Object> item = factory.createParameterItem(label, value, active);
		category.addParameterItem(item);
		assertEquals(1, category.getParameterItems().size());
		assertTrue(category.getParameterItems().containsKey(label));
		category.removeParameterItem(item);
		assertTrue(category.getParameterItems().isEmpty());
	}

	@ParameterizedTest
	@CsvSource({ "Label 1, Description 1, Label 2, Description 2, Label 3, Description 3" })
	@DisplayName("Test adding parameter items with many categories")
	void testAddParameterItemWithCategories(String label1, String description1, String label2, String description2,
			String label3, String description3) {

		ParameterCategory category1 = factory.createParameterCategory(label1, description1);
		ParameterCategory category2 = factory.createParameterCategory(label2, description2);
		ParameterCategory category3 = factory.createParameterCategory(label3, description3);

		ParameterItem<String> item1 = factory.createParameterItem("Item 1", "Value 1", true);
		ParameterItem<Integer> item2 = factory.createParameterItem("Item 2", 2, true);
		ParameterItem<Date> item3 = factory.createParameterItem("Item 3", Date.valueOf(LocalDate.now()), true);

		assertNotEquals(category1, category2);
		assertNotSame(category1, category2);

		category1.addParameterItem(item1);
		category2.addParameterItem(item2);
		category3.addParameterItem(item3);

		Map<String, ParameterItem<?>> items1 = category1.getParameterItems();
		Map<String, ParameterItem<?>> items2 = category2.getParameterItems();
		Map<String, ParameterItem<?>> items3 = category3.getParameterItems();

		assertTrue(items1.containsKey(item1.getLabel()));
		assertEquals(item1, items1.get(item1.getLabel()));

		assertTrue(items2.containsKey(item2.getLabel()));
		assertEquals(item2, items2.get(item2.getLabel()));

		assertTrue(items3.containsKey(item3.getLabel()));
		assertEquals(item3, items3.get(item3.getLabel()));

		assertTrue(category1.isRoot());
		assertTrue(category2.isRoot());
		assertTrue(category3.isRoot());

		assertThrows(InvalidParameterCategoryException.class, () -> category1.addParameterItem(item1));
		assertThrows(NullPointerException.class, () -> category1.addParameterItem(null));
	}

	@ParameterizedTest
	@CsvSource({ "Label 1, Description 1" })
	@DisplayName("Test a category structure")
	void testAddParameterCategoryStructure(String label, String description) {
		ParameterCategory category1 = factory.createParameterCategory(label, description);
		ParameterCategory category2 = factory.createParameterCategory("Label_category2", "Description_category2");
		category2.setParentCategory(category1);
		ParameterCategory category3 = factory.createParameterCategory("Label_category3", "Description_category3");
		category3.setParentCategory(category2);

		assertTrue(category1.isRoot());
		assertFalse(category2.isRoot());
		assertTrue(category1.getAllParentCategories().isEmpty());
		assertTrue(category2.getAllParentCategories().contains(category1));
		assertTrue(category3.getAllParentCategories().contains(category1));
	}

	@ParameterizedTest
	@CsvSource({ "Label 1, Description 1" })
	@DisplayName("Test a category hierarchy")
	void testAddParameterCategoryHierarchy(String label, String description) {
		ParameterCategory category1 = factory.createParameterCategory(label, description);

		ParameterCategory category2 = factory.createParameterCategory("Label_category2", "Description_category2");
		category2.setParentCategory(category1);

		ParameterCategory category3 = factory.createParameterCategory("Label_category3", "Description_category3");
		category3.setParentCategory(category2);

		ParameterCategory category4 = factory.createParameterCategory("Label_category4", "Description_category4");
		InvalidParameterCategoryException circularException = assertThrows(InvalidParameterCategoryException.class,
				() -> category4.setParentCategory(category4));
		String expectedMessage = "Circular dependency detected for parameter";
		String actualMessage = circularException.getMessage();
		assertEquals(expectedMessage, actualMessage);

		ParameterCategory category5 = factory.createParameterCategory(label, description);
		assertThrows(InvalidParameterCategoryException.class, () -> category5.setParentCategory(category3));

		assertTrue(category1.isRoot());
		assertTrue(category1.getAllParentCategories().isEmpty());
		assertTrue(category2.getAllParentCategories().contains(category1));
		assertTrue(category3.getAllParentCategories().containsAll(List.of(category1, category2)));
	}

}
