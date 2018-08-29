package br.edu.fapce.nexti.response;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse<T> {
	private List<String> errors;

	public List<String> getErrors() {
		if (this.errors == null) {
			this.errors = new ArrayList<String>();
		}
		return errors;
	}

	public void addErrors(String error) {
		getErrors().add(error);
	}
}