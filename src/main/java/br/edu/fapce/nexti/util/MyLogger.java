package br.edu.fapce.nexti.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class MyLogger {

	public static String getMessage(String resource, Object dto) {
		String url = "URL = " + resource;
		String data = " ,data = " + dto.toString();
		return url + data;
	}

	public static String getErrorMessage(String resource, BindingResult result) {
		String url = "URL = " + resource;
		List<String> errorList = new ArrayList<>();
		List<ObjectError> allErrors = result.getAllErrors();
		for (ObjectError objectError : allErrors) {
			errorList.add(objectError.getDefaultMessage());
		}
		String data = " ,error = " + errorList;
		return url + data;
	}

	public static String getErrorMessageFromList(String resource, List<String> errors) {
		String url = "URL = " + resource;
		String data = " ,error = " + errors;
		return url + data;
	}

}