package com.iloo.params.model;

import java.util.List;

/**
 * Model class for managing parameter graphs.
 */
public class ParameterGraphsModel {

	private List<String> employees;

	/**
	 * Get the list of employees.
	 *
	 * @return The list of employees.
	 */
	public List<String> getEmployees() {
		return employees;
	}

	/**
	 * Set the list of employees.
	 *
	 * @param employees The list of employees.
	 */
	public void setEmployees(List<String> employees) {
		this.employees = employees;
	}

}
