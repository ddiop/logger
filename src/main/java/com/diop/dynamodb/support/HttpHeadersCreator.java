package com.diop.dynamodb.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

/**
 * Utility class to provide alert information in response HTTP header
 * 
 * @author djibi
 *
 */
public final class HttpHeadersCreator {

	private static final Logger LOG = LoggerFactory.getLogger(HttpHeadersCreator.class);

	private HttpHeadersCreator() {
	}

	private static HttpHeaders createAlert(String message, String param) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-learning-alert", message);
		headers.add("X-learning-params", param);
		return headers;
	}

	public static HttpHeaders createWarning(String param) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-learning-alert", "learning.warning");
		headers.add("X-learning-params", param);
		return headers;
	}

	public static HttpHeaders createEntityCreationAlert(String entityName, String param) {

		return createAlert("learning." + entityName + ".created", param);
	}

	public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {

		return createAlert("learning." + entityName + ".updated", param);
	}

	public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
		return createAlert("learning." + entityName + ".deleted", param);
	}

	public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage) {
		LOG.error("Creation of alert in HTTP response header, {}", defaultMessage);
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-learning-error", "error." + errorKey);
		headers.add("X-learning-params", entityName);
		return headers;
	}
}
