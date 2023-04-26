package com.iloo.params.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ParameterIT {

	@ParameterizedTest(name = "Adding parameter item with label ''{0}'' and value ''{1}''")
	@CsvSource({ "param1, 10, true", "param2, hello, false", "param3, true, true", "param4, 20.5, false" })
	@DisplayName("Test adding parameter items to a category")
	void testAddParameterItem(String label, Object value, boolean active) {
		AParameterFactory factory = new ParameterFactory();
		ParameterCategory category = factory.createParameterCategory("TestCategory", "Test category description");
		ParameterItem<Object> item = factory.createParameterItem(label, value, category, active);

		category.addParameterItem(item);

		assertEquals(1, category.getParameterItems().size());
		assertTrue(category.getParameterItems().containsKey(label));
		assertEquals(item, category.getParameterItemByLabel(label));
	}

}
