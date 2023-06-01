package com.iloo.params.view;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;

/**
 * View class for parameter graphs.
 */
public class ParameterGraphsView {

	private Group chartGroup;
	private Button resetButton;

	/**
	 * Constructs a new instance of ParameterGraphsView.
	 */
	public ParameterGraphsView() {
		chartGroup = new Group();
		resetButton = new Button("R-Zoom");
	}

	/**
	 * Get the chart group.
	 *
	 * @return The chart group.
	 */
	public Group getChartGroup() {
		return chartGroup;
	}

	/**
	 * Get the reset button. Sets the position of the button.
	 *
	 * @return The reset button.
	 */
	public Button getResetButton() {
		createButton();
		resetButton.setTranslateX(10);
		resetButton.setTranslateY(10);
		return resetButton;
	}

	/**
	 * Creates a styled button with a specified label.
	 */
	private void createButton() {
		resetButton.setMinSize(60, 60);
		resetButton.setPrefSize(60, 60);
		resetButton.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		resetButton.setShape(new Circle(40));
	}

}
