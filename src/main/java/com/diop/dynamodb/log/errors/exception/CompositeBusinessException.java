package com.diop.dynamodb.log.errors.exception;

import java.util.List;
import java.util.Map;

public class CompositeBusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String message;

	private final List<Map<String, String[]>> tab;

	public CompositeBusinessException(String message, List<Map<String, String[]>> tab) {
		super();
		this.message = message;
		this.tab = tab;
	}

	public String getMessage() {
		return message;
	}

	public List<Map<String, String[]>> getTab() {
		return tab;
	}

}
