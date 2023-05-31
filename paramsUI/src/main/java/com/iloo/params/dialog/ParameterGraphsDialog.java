package com.iloo.params.dialog;

import com.iloo.params.controller.ParameterGraphsController;
import com.iloo.params.model.ParameterGraphsModel;
import com.iloo.params.view.ParameterGraphsView;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * A dialog to view the parameter structure.
 */
public class ParameterGraphsDialog extends Application {

	@Override
	public void start(Stage primaryStage) {
		var model = new ParameterGraphsModel();
		var view = new ParameterGraphsView();
		var controller = new ParameterGraphsController(model, view);

		// Initialize the controller
		controller.initialize();

		// Set up the UI elements and configure event handling
		view.getResetButton().setOnAction(event -> controller.resetZoom());

		// Create the root StackPane and set the view components
		var root = new StackPane();
		StackPane.setAlignment(view.getResetButton(), Pos.TOP_LEFT);
		StackPane.setAlignment(view.getChartGroup(), Pos.CENTER);
		root.getChildren().addAll(view.getResetButton(), view.getChartGroup());

		// Create the scene and set it to the stage
		var scene = new Scene(root, ParameterGraphsController.getChartWidth(),
				ParameterGraphsController.getChartHeight());

		// Enable zooming using mouse scroll
		controller.enableZoom(scene, view.getChartGroup());

		primaryStage.setScene(scene);

		primaryStage.setAlwaysOnTop(true);

		// Set the minimum stage size to match the default scene size
		primaryStage.setMinWidth(scene.getWidth());
		primaryStage.setMinHeight(scene.getHeight());

		// Set the maximum stage size to match the computer resolution
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		primaryStage.setMaxWidth(screenBounds.getWidth());
		primaryStage.setMaxHeight(screenBounds.getHeight());

		primaryStage.setTitle("Hierarchical Org Chart Example");
		primaryStage.show();
	}

}
