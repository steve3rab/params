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

class ParameterIT {

	private static IParameterFactory factory;

	@BeforeAll
	static void initAll() {
		factory = new ParameterFactory();
	}

	@ParameterizedTest(name = "Create category with label ''{0}'' and description ''{1}''")
	@CsvSource({ "Label 1, Description 1", "Label 2, Description 2" })
	@DisplayName("Test a category")
	void testParameterCategory(String label, String description) {
		IParameterCategory category = factory.createParameterCategory(label, description);
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
		IParameterCategory category = factory.createParameterCategory(categoryLabel, "Test category");
		IParameterItem<String> item = factory.createParameterItem(label, value, active);
		item.setActive(true);
		category.addParameterItem(item);
		assertEquals(label, item.getLabel());
		assertEquals(value, item.getValue());
		assertTrue(item.isActive());
	}

	@ParameterizedTest(name = "Creating parameter item with label ''{0}'' and active ''{2}''")
	@CsvSource({ "Label 1, false" })
	@DisplayName("Test a parameter item")
	void testParameterItemValueType(String label, boolean active) {
		String valueStr = "Value";
		IParameterItem<String> item1 = factory.createParameterItem(label, valueStr, active);
		assertEquals(label, item1.getLabel());
		assertEquals(valueStr, item1.getValue());
		assertFalse(item1.isActive());

		File valueFile = new File(valueStr);
		assertThrows(InvalidParameterItemException.class, () -> factory.createParameterItem(label, valueFile, active));

		int valueInt = 2;
		IParameterItem<Integer> item2 = factory.createParameterItem(label, valueInt, active);
		assertSame(valueInt, item2.getValue());
	}

	@ParameterizedTest(name = "Adding and removing parameter item with label ''{0}'' and value ''{1}''")
	@CsvSource({ "param1, 10, true" })
	@DisplayName("Test adding and removing parameter items to a category")
	void testAddRemoveParameterItem(String label, Object value, boolean active) {
		IParameterCategory category = factory.createParameterCategory("Label 1", "Description 1");
		IParameterItem<Object> item = factory.createParameterItem(label, value, active);
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

		IParameterCategory category1 = factory.createParameterCategory(label1, description1);
		IParameterCategory category2 = factory.createParameterCategory(label2, description2);
		IParameterCategory category3 = factory.createParameterCategory(label3, description3);

		IParameterItem<String> item1 = factory.createParameterItem("Item 1", "Value 1", true);
		IParameterItem<Integer> item2 = factory.createParameterItem("Item 2", 2, true);
		IParameterItem<Date> item3 = factory.createParameterItem("Item 3", Date.valueOf(LocalDate.now()), true);

		assertNotEquals(category1, category2);
		assertNotSame(category1, category2);

		category1.addParameterItem(item1);
		category2.addParameterItem(item2);
		category3.addParameterItem(item3);

		Map<String, IParameterItem<?>> items1 = category1.getParameterItems();
		Map<String, IParameterItem<?>> items2 = category2.getParameterItems();
		Map<String, IParameterItem<?>> items3 = category3.getParameterItems();

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
		IParameterCategory category1 = factory.createParameterCategory(label, description);
		IParameterCategory category2 = factory.createParameterCategory("Label_category2", "Description_category2");
		category2.setParentCategory(category1);
		IParameterCategory category3 = factory.createParameterCategory("Label_category3", "Description_category3");
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
		IParameterCategory category1 = factory.createParameterCategory(label, description);

		IParameterCategory category2 = factory.createParameterCategory("Label_category2", "Description_category2");
		category2.setParentCategory(category1);

		IParameterCategory category3 = factory.createParameterCategory("Label_category3", "Description_category3");
		category3.setParentCategory(category2);

		IParameterCategory category4 = factory.createParameterCategory("Label_category4", "Description_category4");
		InvalidParameterCategoryException circularException = assertThrows(InvalidParameterCategoryException.class,
				() -> category4.setParentCategory(category4));
		String expectedMessage = "Circular dependency detected for parameter";
		String actualMessage = circularException.getMessage();
		assertEquals(expectedMessage, actualMessage);

		IParameterCategory category5 = factory.createParameterCategory(label, description);
		assertThrows(InvalidParameterCategoryException.class, () -> category5.setParentCategory(category3));

		IParameterCategory category6 = factory.createParameterCategory("Label_category6", "Description_category6");
		category6.setParentCategory(category2);

		IParameterCategory category7 = factory.createParameterCategory("Label_category7", "Description_category7");
		category6.setChildCategory(category7);

		assertTrue(category1.isRoot());
		assertTrue(category1.getAllParentCategories().isEmpty());
		assertTrue(category2.getAllParentCategories().contains(category1));
		assertTrue(category3.getAllParentCategories().containsAll(List.of(category1, category2)));
		// category 3 is a the third level
		assertSame(3, category3.getLevel().getVertical());
		// category 2 has two children
		assertSame(2, category2.getLevel().getHorizontal());
		// category6 is parent of category7
		assertEquals(category6, category7.getParentCategory().get());
	}

	@ParameterizedTest
	@CsvSource({ "Label 1, Description 1" })
	@DisplayName("Test a category parameter hierarchy")
	void testParameterItemCategoryHierarchy(String label, String description) {
		IParameterCategory category1 = factory.createParameterCategory(label, description);
		category1.addParameterItem(factory.createParameterItem("Label_parameter1", "Value_parameter1", false));
		category1.addParameterItem(factory.createParameterItem("Label_parameter2", "Value_parameter2", false));
		category1.addParameterItem(factory.createParameterItem("Label_parameter3", "Value_parameter3", false));

		IParameterCategory category2 = factory.createParameterCategory("Label_category2", "Description_category2");
		category2.addParameterItem(factory.createParameterItem("Label_parameter4", "Value_parameter4", false));
		category2.setParentCategory(category1);

		IParameterCategory category3 = factory.createParameterCategory("Label_category3", "Description_category3");
		category3.addParameterItem(factory.createParameterItem("Label_parameter1", "Value_parameter6", false));
		category3.addParameterItem(factory.createParameterItem("Label_parameter5", "Value_parameter5", false));
		category3.setParentCategory(category2);

		Map<String, IParameterItem<?>> parameters = category3.getAllParentParameterItems();
		assertSame(5, parameters.size());
		assertEquals("Value_parameter6", String.valueOf(parameters.get("Label_parameter1").getValue()));
	}

}
