package com.diop.dynamodb.log.errors;

/**
 * Provides field error details with an error message
 *
 * @author Djibi
 *
 */
public class FieldErrorDetails {
	private String objectName;

	private String field;

	private String message;

	public FieldErrorDetails(String objectName, String field, String message) {
		this.objectName = objectName;
		this.field = field;
		this.message = message;
	}

	public String getObjectName() {
		return objectName;
	}

	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}
}
