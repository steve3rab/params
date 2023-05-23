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
	private static final double CHART_WIDTH = 600.0;

	/**
	 * Chart height.
	 */
	private static final double CHART_HEIGHT = 400.0;

	/**
	 * Chart minimum scale.
	 */
	private static final double MIN_SCALE = 0.1;

	/**
	 * Chart maximum scale.
	 */
	private static final double MAX_SCALE = 10.0;

	/**
	 * Chart scale delta.
	 */
	private static final double SCALE_DELTA = 1.1;

	@Override
	public void start(Stage primaryStage) {
		// Create the root StackPane
		StackPane root = new StackPane();

		// Create the root node
		StackPane ceoStackPane = createCircle("CEO");
		List<StackPane> children = List.of(createCircle("Employee 1"), createCircle("Employee 2"),
				createCircle("Employee 3"), createCircle("Employee 4"), createCircle("Employee 5"),
				createCircle("Employee 6"));
		Group group = createChart(ceoStackPane, children);

		// Create the scene with a minimum size
		Scene scene = new Scene(root, CHART_WIDTH, CHART_HEIGHT);

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

	/**
	 * Creates the organizational chart with the given parent and children nodes.
	 *
	 * @param parent   The parent node.
	 * @param children The list of children nodes.
	 * @return The Group containing the organizational chart.
	 */
	private Group createChart(StackPane parent, List<StackPane> children) {
		Group group = new Group();

		Circle parentCircle = (Circle) parent.getChildren().get(0);
		parent.setLayoutX(CHART_WIDTH / 2);
		parent.setLayoutY(CHART_HEIGHT / 2);

		group.getChildren().add(parent);

		double childOffsetX = (parentCircle.getRadius() + 50.0) * 2;
		double childOffsetY = (parentCircle.getRadius() + 50.0);

		for (int i = 0; i < children.size(); i++) {
			StackPane child = children.get(i);

			double childLayoutX;
			if (i < (children.size() / 2)) {
				childLayoutX = parent.getLayoutX() - (((children.size() / 2.0) - i) * childOffsetX);
			} else {
				childLayoutX = parent.getLayoutX() + ((i - (children.size() / 2.0)) * childOffsetX);
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

	/**
	 * Creates a StackPane representing a circle node with the given text.
	 *
	 * @param text The text to display inside the circle.
	 * @return The StackPane representing the circle node.
	 */
	private StackPane createCircle(String text) {
		final double radius = 40.0;

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

	/**
	 * Creates tangent lines between the source and target nodes.
	 *
	 * @param source The source node.
	 * @param target The target node.
	 * @return The Line representing the tangent lines.
	 */
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

	/**
	 * Enables zooming of the chart using mouse scroll.
	 *
	 * @param scene The Scene to enable zooming on.
	 * @param group The Group representing the chart.
	 */
	private void enableZoom(Scene scene, Group group) {
		scene.addEventFilter(ScrollEvent.ANY, event -> {
			event.consume();

			double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA : 1 / SCALE_DELTA;

			Bounds groupBounds = group.getLayoutBounds();
			double groupCenterX = (groupBounds.getMinX() + groupBounds.getMaxX()) / 2;
			double groupCenterY = (groupBounds.getMinY() + groupBounds.getMaxY()) / 2;

			group.getTransforms().add(new Scale(scaleFactor, scaleFactor, groupCenterX, groupCenterY));

			// Ensure the scale stays within the defined limits
			double currentScale = group.getScaleX();
			if (currentScale < MIN_SCALE) {
				group.getTransforms()
						.add(new Scale(MIN_SCALE / currentScale, MIN_SCALE / currentScale, groupCenterX, groupCenterY));
			} else if (currentScale > MAX_SCALE) {
				group.getTransforms()
						.add(new Scale(MAX_SCALE / currentScale, MAX_SCALE / currentScale, groupCenterX, groupCenterY));
			}
		});
	}

	/**
	 * Creates a Button that resets the zoom of the chart.
	 *
	 * @param group The Group representing the chart.
	 * @return The Button that resets the zoom.
	 */
	private Button createResetButton(Group group) {
		Button resetButton = new Button("Reset Zoom");
		resetButton.setOnAction(event -> group.getTransforms().clear());
		resetButton.setTranslateX(10);
		resetButton.setTranslateY(10);
		return resetButton;
	}
}
