package com.diop.dynamodb.log.errors;


import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.diop.dynamodb.log.errors.exception.BusinessException;
import com.diop.dynamodb.log.errors.exception.CompositeBusinessException;
import com.diop.dynamodb.log.errors.exception.TechnicalException;
import com.diop.dynamodb.support.HttpHeadersCreator;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Controller advice to transform the exceptions to json structure
 *
 * @author djibi
 *
 */
@ControllerAdvice
public class ControllerExceptionTranslator {


	@Inject
	private MessageSource messageSource;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorMessage processValidationError(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		return processFieldErrors(fieldErrors);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorMessage processHttpMessageNotReadableError(HttpMessageNotReadableException ex) {
		return new ErrorMessage(ErrorConstants.ERR_HTTP_MESSAGE_NOT_READABLE, ex.getMessage());
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ResponseBody
	public ErrorMessage processMethodNotSupportedError(HttpRequestMethodNotSupportedException ex) {
		return new ErrorMessage(ErrorConstants.ERR_METHOD_NOT_SUPPORTED, ex.getMessage());
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorMessage processMissingParameterError(MissingServletRequestParameterException ex) {
		return new ErrorMessage(ErrorConstants.ERR_PARAMETER, ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorMessage processTypeMismatchError(MissingServletRequestParameterException ex) {
		return new ErrorMessage(ErrorConstants.ERR_PARAMETER, ex.getMessage());
	}

	private ErrorMessage processFieldErrors(List<FieldError> fieldErrors) {
		ErrorMessage errorInfo = new ErrorMessage(ErrorConstants.ERR_VALIDATION, "La structure de donnée n'est pas valide");
		fieldErrors.stream().forEach(fe -> errorInfo.addFieldError(fe.getObjectName(), fe.getField(), fe.getDefaultMessage()));
		return errorInfo;
	}

	@ExceptionHandler(BusinessException.class)
	@ResponseBody
	public ResponseEntity<ErrorMessage> processBusinessException(BusinessException bex) {
		String code = "error." + bex.getMessage();
		BodyBuilder builder = ResponseEntity.status(bex.getHttpStatus());
		return builder.body(new ErrorMessage(code, messageSource.getMessage(code, bex.getParams(), code, Locale.FRENCH)));
	}


	@ExceptionHandler(TechnicalException.class)
	@ResponseBody
	public ResponseEntity<ErrorMessage> processTechnicalException(TechnicalException tex) {
		String code = "error." + tex.getMessage();
		BodyBuilder builder = ResponseEntity.status(tex.getHttpStatus());
		return builder.body(new ErrorMessage(code, messageSource.getMessage(code, tex.getParams(), code, Locale.FRENCH)));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> processException(Exception ex) {
		BodyBuilder builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
		return builder.body(new ErrorMessage(ErrorConstants.ERR_INTERNAL_SERVER));
	}

	@ExceptionHandler(CompositeBusinessException.class)
	public ResponseEntity<GlobalErrorMessage> processCompositeBusinessException(CompositeBusinessException bex) {
		BodyBuilder builder = ResponseEntity.status(HttpStatus.ACCEPTED);
		List<ErrorMessage> messages = new ArrayList<>();
		bex.getTab().forEach(elt -> {
			elt.entrySet().stream().forEach(entry -> messages.add(new ErrorMessage(entry.getKey(), messageSource.getMessage("error." + entry.getKey(), entry.getValue(), Locale.FRENCH))));
		});
		return builder.headers(HttpHeadersCreator.createWarning(bex.getMessage())).body(new GlobalErrorMessage(bex.getMessage(), messages));
	}
}
