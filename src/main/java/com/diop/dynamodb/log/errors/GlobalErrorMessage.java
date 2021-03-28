package com.diop.dynamodb.log.errors;

import java.util.List;

public class GlobalErrorMessage {

	private String identifiant;
	private List<ErrorMessage> errorMessages;

	public GlobalErrorMessage(String identifiant, List<ErrorMessage> errorMessages) {
		super();
		this.identifiant = identifiant;
		this.errorMessages = errorMessages;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public List<ErrorMessage> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<ErrorMessage> errorMessages) {
		this.errorMessages = errorMessages;
	}

}
