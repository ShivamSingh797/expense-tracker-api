package com.projects.expense_tracker_api.exception;

public class CategoryAlreadyExistsException extends RuntimeException {

	public CategoryAlreadyExistsException(String message) {
		super(message);
	}
}
