package br.edu.fapce.nexti.validator;

import java.util.List;

public class MyValidator {
	public static void addErrorsWhenNull(List<String> errors, String message, Object object) {
		if (object == null) {
			errors.add(message);
		}
	}

	public static void addErrorsWhenNotNull(List<String> errors, String message, Object object) {
		if (object != null) {
			errors.add(message);
		}
	}

}