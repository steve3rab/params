package com.iloo.params.core;

/**
 * This class represents a pair of values for horizontal and vertical.
 */
class ParamaterLevel {

	private int horizontal;
	private int vertical;

	/**
	 * Constructs a new ParamaterLevel object with default horizontal and vertical
	 * values of 0.
	 */
	public ParamaterLevel() {
		this.horizontal = 0;
		this.vertical = 1;
	}

	/**
	 * Returns the horizontal value of this pair.
	 *
	 * @return the horizontal value of this pair
	 */
	public int getHorizontal() {
		return horizontal;
	}

	/**
	 * Returns the vertical value of this pair.
	 *
	 * @return the vertical value of this pair
	 */
	public int getVertical() {
		return vertical;
	}

	/**
	 * Sets the horizontal value of this pair.
	 */
	public void setHorizontal(int horizontal) {
		this.horizontal = horizontal;
	}

	/**
	 * Sets the vertical value of this pair
	 */
	public void setVertical(int vertical) {
		this.vertical = vertical;
	}

	/**
	 * Sets the horizontal value of this pair to the next integer value.
	 */
	public void incrementHorizontal() {
		this.horizontal++;
	}

	/**
	 * Sets the vertical value of this pair to the next integer value.
	 */
	public void incrementVertical() {
		this.vertical++;
	}

}
