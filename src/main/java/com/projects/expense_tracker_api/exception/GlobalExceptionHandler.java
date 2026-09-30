package com.projects.expense_tracker_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleResourceNotFound(ResourceNotFoundException ex) {

		return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				java.time.LocalDateTime.now().toString());
	}

	@ExceptionHandler(EmailAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorResponse handleEmailAlreadyExists(EmailAlreadyExistsException ex) {

		return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage(),
				java.time.LocalDateTime.now().toString());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleValidationException(MethodArgumentNotValidException ex) {

		return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Invalid input",
				java.time.LocalDateTime.now().toString());
	}

	@ExceptionHandler(CategoryAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorResponse handleCategoryAlreadyExists(CategoryAlreadyExistsException ex) {
		return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage(),
				java.time.LocalDateTime.now().toString());
	}
}