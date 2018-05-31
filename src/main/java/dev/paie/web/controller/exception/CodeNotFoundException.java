package dev.paie.web.controller.exception;

public class CodeNotFoundException extends RuntimeException {

	private String code;

	public CodeNotFoundException(String code) {
		super();
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
