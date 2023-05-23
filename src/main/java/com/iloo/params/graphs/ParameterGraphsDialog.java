package com.iloo.params.graphs;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * A dialog to view the parameter structure.
 */
public class ParameterGraphsDialog extends Application {

	/**
	 * Chart width.
	 */
	private static final double CHARTWIDTH = 600.0d;

	/**
	 * Chart height.
	 */
	private static final double CHARTHEIGHT = 400.0d;

	/**
	 * Chart minimum scale.
	 */
	private static final double MINSCALE = 0.1d;

	/**
	 * Chart maximum scale.
	 */
	private static final double MAXSCALE = 10.0d;

	/**
	 * Chart scale delta.
	 */
	private static final double SCALEDELTA = 1.1d;

	@Override
	public void start(Stage primaryStage) {
		// Create the root StackPane
		StackPane root = new StackPane();

		// Create the root node
		Group group = new Group();

		// Create nodes for the org chart
		StackPane ceoStackPane = createCircle(40, "CEO");
		Circle ceoCircle = (Circle) ceoStackPane.getChildren().get(0);
		StackPane managerStackPane = createCircle(40, "Manager");
		Circle managerCircle = (Circle) managerStackPane.getChildren().get(0);
		StackPane employee1StackPane = createCircle(40, "Employee 1");
		StackPane employee2StackPane = createCircle(40, "Employee 2");

		ceoStackPane.setLayoutX(CHARTWIDTH / 2);
		ceoStackPane.setLayoutY(CHARTHEIGHT / 2);

		managerStackPane.setLayoutX(ceoStackPane.getLayoutX() + ceoCircle.getRadius() + 50);
		managerStackPane.setLayoutY(ceoStackPane.getLayoutY());

		employee1StackPane.setLayoutX(managerStackPane.getLayoutX() - managerCircle.getRadius() - 50);
		employee1StackPane.setLayoutY(managerStackPane.getLayoutY() + managerCircle.getRadius() + 50);

		employee2StackPane.setLayoutX(managerStackPane.getLayoutX() + managerCircle.getRadius() + 50);
		employee2StackPane.setLayoutY(managerStackPane.getLayoutY() + managerCircle.getRadius() + 50);

		// Add the nodes to the root node
		group.getChildren().addAll(ceoStackPane, managerStackPane, employee1StackPane, employee2StackPane);

		// Create lines connecting the circles
		Line line1 = createTangentLines(ceoStackPane, managerStackPane);
		Line line2 = createTangentLines(managerStackPane, employee1StackPane);
		Line line3 = createTangentLines(managerStackPane, employee2StackPane);

		group.getChildren().addAll(line1, line2, line3);

		// Create the scene with a minimum size
		Scene scene = new Scene(root, CHARTWIDTH, CHARTHEIGHT);

		// Enable zooming using mouse scroll
		enableZoom(scene, group);

		Button resetButton = createResetButton(group);

		// Set the content Group as the child of the root StackPane
		StackPane.setAlignment(group, Pos.CENTER);
		StackPane.setAlignment(resetButton, Pos.TOP_LEFT);
		root.getChildren().addAll(group, resetButton);

		// Set the stage title and scene
		primaryStage.setTitle("Hierarchical Org Chart Example");
		primaryStage.setScene(scene);
		primaryStage.setAlwaysOnTop(true);

		// Set the minimum stage size to match the default scene size
		primaryStage.setMinWidth(scene.getWidth());
		primaryStage.setMinHeight(scene.getHeight());

		// Set the maximum stage size to match the computer resolution
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		primaryStage.setMaxWidth(screenBounds.getWidth());
		primaryStage.setMaxHeight(screenBounds.getHeight());

		// Show the stage
		primaryStage.show();
	}

	private StackPane createCircle(double radius, String text) {
		Circle circle = new Circle(radius);
		circle.setFill(Color.BLUE);

		Label label = new Label(text);
		label.setTextFill(Color.WHITE); // Set label color to white

		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(circle, label);

		// Position label inside the circle
		StackPane.setAlignment(label, Pos.CENTER);

		// Set the size of the stackPane to match the circle's size
		stackPane.setMinSize(2 * radius, 2 * radius);
		stackPane.setPrefSize(2 * radius, 2 * radius);
		stackPane.setMaxSize(2 * radius, 2 * radius);

		return stackPane;
	}

	private Line createTangentLines(StackPane source, StackPane target) {
		Circle sourceCircle = (Circle) source.getChildren().get(0);
		Circle targetCircle = (Circle) target.getChildren().get(0);

		double sourceRadius = sourceCircle.getRadius();
		double targetRadius = targetCircle.getRadius();

		double sourceCenterX = source.getLayoutX() + sourceRadius;
		double sourceCenterY = source.getLayoutY() + sourceRadius;
		double targetCenterX = target.getLayoutX() + targetRadius;
		double targetCenterY = target.getLayoutY() + targetRadius;

		double angle = Math.atan2(targetCenterY - sourceCenterY, targetCenterX - sourceCenterX);

		double sourceX = sourceCenterX + (Math.cos(angle) * sourceRadius);
		double sourceY = sourceCenterY + (Math.sin(angle) * sourceRadius);

		double targetX = targetCenterX - (Math.cos(angle) * targetRadius);
		double targetY = targetCenterY - (Math.sin(angle) * targetRadius);

		return new Line(sourceX, sourceY, targetX, targetY);
	}

	private void enableZoom(Scene scene, Group group) {
		scene.addEventFilter(ScrollEvent.ANY, event -> {
			event.consume();

			double scaleFactor = (event.getDeltaY() > 0) ? SCALEDELTA : 1 / SCALEDELTA;

			Bounds groupBounds = group.getLayoutBounds();
			double groupCenterX = (groupBounds.getMinX() + groupBounds.getMaxX()) / 2;
			double groupCenterY = (groupBounds.getMinY() + groupBounds.getMaxY()) / 2;

			group.getTransforms().add(new Scale(scaleFactor, scaleFactor, groupCenterX, groupCenterY));

			// Ensure the scale stays within the defined limits
			double currentScale = group.getScaleX();
			if (currentScale < MINSCALE) {
				group.getTransforms()
						.add(new Scale(MINSCALE / currentScale, MINSCALE / currentScale, groupCenterX, groupCenterY));
			} else if (currentScale > MAXSCALE) {
				group.getTransforms()
						.add(new Scale(MAXSCALE / currentScale, MAXSCALE / currentScale, groupCenterX, groupCenterY));
			}
		});
	}

	private Button createResetButton(Group group) {
		Button resetButton = new Button("Reset Zoom");
		resetButton.setOnAction(event -> group.getTransforms().clear());
		resetButton.setTranslateX(10);
		resetButton.setTranslateY(10);
		return resetButton;
	}
}
