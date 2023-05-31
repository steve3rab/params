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
		resetButton = createButton("R-Zoom");
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
		resetButton.setTranslateX(10);
		resetButton.setTranslateY(10);
		return resetButton;
	}

	/**
	 * Creates a styled button with a specified label.
	 *
	 * @param label The label text for the button.
	 * @return The styled button.
	 */
	private Button createButton(String label) {
		Button button = new Button(label);
		button.setMinSize(60, 60);
		button.setPrefSize(60, 60);
		button.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		button.setShape(new Circle(40));
		return button;
	}

}
