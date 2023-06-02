package com.iloo.params.dialog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import com.iloo.params.controller.ParameterGraphsController;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Unit test for the ParameterGraphsDialog class.
 */
@ExtendWith(ApplicationExtension.class)
class ParameterGraphsDialogIT {

	private Stage primaryStage;

	/**
	 * Starts the ParameterGraphsDialog and initializes the UI elements.
	 *
	 * @param stage the primary stage for the test
	 */
	@Start
	public void start(Stage stage) {
		primaryStage = stage;
		ParameterGraphsDialog dialog = new ParameterGraphsDialog();
		dialog.start(primaryStage);
	}

	/**
	 * Tests the start() method of ParameterGraphsDialog. Verifies that the UI
	 * elements are correctly initialized.
	 */
	@Test
	void testStart() {
		// Access the UI elements and perform assertions as before
		StackPane root = (StackPane) Stage.getWindows().get(0).getScene().getRoot();
		assertEquals(2, root.getChildren().size());
		assertTrue(root.getChildren().get(0) instanceof Button);

		Button resetButton = (Button) root.getChildren().get(0);
		assertNotNull(resetButton.getOnAction());

		// Additional assertions
		assertEquals(Pos.TOP_LEFT, StackPane.getAlignment(resetButton));
		assertEquals(ParameterGraphsController.getChartWidth(), primaryStage.getScene().getWidth());
		assertEquals(ParameterGraphsController.getChartHeight(), primaryStage.getScene().getHeight());
		assertTrue(primaryStage.isAlwaysOnTop());
		assertEquals("Hierarchical Org Chart Example", primaryStage.getTitle());
	}
}
