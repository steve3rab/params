package com.iloo.params.view;

import javafx.scene.Group;
import javafx.scene.control.Button;

/**
 * View class for parameter graphs.
 */
public class ParameterGraphsView {

	private Group chartGroup;
	private Button resetButton;

	/**
	 * Constructs a new instance of ParameterGraphsView. Initializes the chartGroup
	 * and resetButton.
	 */
	public ParameterGraphsView() {
		chartGroup = new Group();
		resetButton = new Button("Reset Zoom");
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

}
