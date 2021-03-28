package com.diop.dynamodb.log.errors;

import java.util.ArrayList;
import java.util.List;

/**
 * View bean to transfer error message with a list of field errors
 * 
 * @author Djibi
 *
 */
public class ErrorMessage {
	private String message;

	private String description;


	private List<FieldErrorDetails> fieldErrors;

	public ErrorMessage(String message) {
		this(message, null, null);
	}

	public ErrorMessage(String message, String description) {
		this(message, description, null);
	}

	public ErrorMessage(String message, String description, List<FieldErrorDetails> fieldErrors) {
		this.message = message;
		this.description = description;
		this.fieldErrors = fieldErrors;
	}

	public void addFieldError(String objectName, String field, String message) {
		if (fieldErrors == null) {
			fieldErrors = new ArrayList<>();
		}
		fieldErrors.add(new FieldErrorDetails(objectName, field, message));
	}

	public String getMessage() {
		return message;
	}

	public String getDescription() {
		return description;
	}

	public List<FieldErrorDetails> getFieldErrors() {
		return fieldErrors;
	}

}
