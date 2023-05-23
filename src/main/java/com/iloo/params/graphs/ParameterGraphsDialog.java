package com.iloo.params.graphs;

import java.util.List;

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
		StackPane ceoStackPane = createCircle("CEO");
		StackPane employee1StackPane = createCircle("Employee 1");
		StackPane employee2StackPane = createCircle("Employee 2");
		StackPane employee3StackPane = createCircle("Employee 3");
		StackPane employee4StackPane = createCircle("Employee 4");
		StackPane employee5StackPane = createCircle("Employee 5");
		StackPane employee6StackPane = createCircle("Employee 6");
		Group group = createChart(ceoStackPane, List.of(employee1StackPane, employee2StackPane, employee3StackPane,
				employee4StackPane, employee5StackPane, employee6StackPane));

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

	private Group createChart(StackPane parent, List<StackPane> children) {
		Group group = new Group();

		Circle parentCircle = (Circle) parent.getChildren().get(0);
		parent.setLayoutX(CHARTWIDTH / 2);
		parent.setLayoutY(CHARTHEIGHT / 2);

		group.getChildren().add(parent);

		double childOffsetX = (parentCircle.getRadius() + 50.0d) * 2;
		double childOffsetY = (parentCircle.getRadius() + 50.0d);

		for (int i = 0; i < children.size(); i++) {
			StackPane child = children.get(i);

			double childLayoutX;
			if (i < (children.size() / 2)) {
				childLayoutX = parent.getLayoutX() - (((children.size() / 2.0d) - i) * childOffsetX);
			} else {
				childLayoutX = parent.getLayoutX() + ((i - (children.size() / 2.0d)) * childOffsetX);
			}

			double childLayoutY = parent.getLayoutY() + childOffsetY;

			child.setLayoutX(childLayoutX);
			child.setLayoutY(childLayoutY);

			group.getChildren().add(child);

			Line line = createTangentLines(parent, child);
			group.getChildren().add(line);
		}

		return group;
	}

	private StackPane createCircle(String text) {
		final double radius = 40.0d;

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
