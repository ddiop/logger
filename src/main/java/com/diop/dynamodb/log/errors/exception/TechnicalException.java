package com.diop.dynamodb.log.errors.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;

public class TechnicalException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String message;

	private final String[] params;

	private final HttpStatus httpStatus;

	public TechnicalException(String message, String... params) {
		super();
		this.message = message;
		this.params = params;
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public TechnicalException(String message, Throwable cause, String... params) {
		super(cause);
		this.message = message;
		this.params = params;
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public TechnicalException(HttpStatus httpStatus, String message, String... params) {
		super();
		this.message = message;
		this.params = params;
		this.httpStatus = httpStatus;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public String[] getParams() {
		return Arrays.copyOf(this.params, this.params.length);
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
