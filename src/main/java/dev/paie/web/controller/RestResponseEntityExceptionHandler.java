package dev.paie.web.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import dev.paie.web.controller.exception.CodeNotFoundException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = DataIntegrityViolationException.class)
	protected ResponseEntity<Object> deleteException(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = "DataIntegrityViolationException";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler(value = CodeNotFoundException.class)
	protected ResponseEntity<Object> codeNotFound(CodeNotFoundException ex, WebRequest request) {
		String bodyOfResponse = ex.getCode() + " : Code de cotisations non trouvé";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

}
