package com.diop.dynamodb.log.errors.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;

public class  BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String message;

	private final String[] params;

	private final HttpStatus httpStatus;

	public BusinessException(String message, String... params) {
		super();
		this.message = message;
		this.params = params;
		this.httpStatus = HttpStatus.BAD_REQUEST;
	}

	public BusinessException(HttpStatus httpStatus, String message, String... params) {
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
		if(params == null){
			return null;
		}else{
			return Arrays.copyOf(this.params, this.params.length);
		}
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
