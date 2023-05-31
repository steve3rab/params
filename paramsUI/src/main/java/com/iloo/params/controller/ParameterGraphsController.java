package com.iloo.params.controller;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import com.iloo.params.model.ParameterGraphsModel;
import com.iloo.params.view.ParameterGraphsView;

import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Scale;

/**
 * Controller class for managing parameter graphs.
 */
public class ParameterGraphsController {

	/**
	 * Chart minimum scale.
	 */
	private static final double MIN_SCALE = 0.1d;

	/**
	 * Chart maximum scale.
	 */
	private static final double MAX_SCALE = 10.0d;

	/**
	 * Chart scale delta.
	 */
	private static final double SCALE_DELTA = 1.1d;

	/**
	 * Chart width.
	 */
	private static final double CHART_WIDTH = 800.0d;

	/**
	 * Shift amount.
	 */
	private static final double SHIFT_AMOUNT = 10.0d;

	/**
	 * Chart height.
	 */
	private static final double CHART_HEIGHT = 500.0;

	private final ParameterGraphsModel model;
	private final ParameterGraphsView view;

	/**
	 * Constructs a new instance of ParameterGraphsController.
	 */
	public ParameterGraphsController(@NonNull ParameterGraphsModel model, @NonNull ParameterGraphsView view) {
		this.model = model;
		this.view = view;
	}

	/**
	 * Initializes the parameter graphs controller. This method should be called
	 * after setting the model and view.
	 */
	public void initialize() {
		if ((model != null) && (view != null)) {
			model.setEmployees(new ArrayList<>());
			model.getEmployees().add("Employee 0");
			model.getEmployees().add("Employee 1");

			updateView();
		}
	}

	private void updateView() {
		List<StackPane> children = new ArrayList<>();
		for (String employee : model.getEmployees()) {
			children.add(createCircle(employee));
		}

		Group chartGroup = createChart(createCircle("CEO"), children);
		view.getChartGroup().getChildren().setAll(chartGroup);
	}

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
	public void enableZoom(Scene scene, Group group) {
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
	 * Resets the zoom of the chart.
	 */
	public void resetZoom() {
		view.getChartGroup().getTransforms().clear();
	}

	/**
	 * Handles the shift action by shifting the chart group. The amount of shift is
	 * defined by the shiftAmount variable.
	 *
	 * @param scene The Scene to enable shift on.
	 */
	public void enableShift(Scene scene) {
		scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			event.consume();

			double currentTranslateX = view.getChartGroup().getTranslateX();
			double currentTranslateY = view.getChartGroup().getTranslateY();
			double newTranslateX = 0.0d;
			double newTranslateY = 0.0d;

			KeyCode keyCode = event.getCode();
			switch (keyCode) {
			case RIGHT:
				// Handle shift right action
				newTranslateX = currentTranslateX + SHIFT_AMOUNT;
				view.getChartGroup().setTranslateX(newTranslateX);
				break;
			case LEFT:
				// Handle shift left action

				newTranslateX = currentTranslateX - SHIFT_AMOUNT;
				view.getChartGroup().setTranslateX(newTranslateX);
				break;
			case UP:
				// Handle shift up action
				newTranslateY = currentTranslateY - SHIFT_AMOUNT;
				view.getChartGroup().setTranslateY(newTranslateY);
				break;
			case DOWN:
				// Handle shift down action
				newTranslateY = currentTranslateY + SHIFT_AMOUNT;
				view.getChartGroup().setTranslateY(newTranslateY);
				break;
			default:
				// Ignore other keys
				break;
			}
		});
	}

	public static double getChartWidth() {
		return CHART_WIDTH;
	}

	public static double getChartHeight() {
		return CHART_HEIGHT;
	}

}
