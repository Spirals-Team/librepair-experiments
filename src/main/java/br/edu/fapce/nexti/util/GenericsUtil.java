package br.edu.fapce.nexti.util;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import br.edu.fapce.nexti.response.ErrorResponse;


public class GenericsUtil {
	@SuppressWarnings("rawtypes")
	public static <T> ResponseEntity objectToResponse(T t) {
		// Response<T> response = new Response<>();
		// response.setData(t);
		// Date d = new Date();
//		for (int i = 0; i < 500000; i++) {
//			System.out.println(i);
//		}
		// System.out.println("Digite");
		// new Scanner(System.in).nextLine();

		return ResponseEntity.ok(t);
	}

	@SuppressWarnings("rawtypes")
	public static ResponseEntity errorsToResponse(BindingResult result) {
		ErrorResponse<String> errorResponse = new ErrorResponse<String>();
		result.getAllErrors().forEach(e -> errorResponse.addErrors(e.getDefaultMessage()));
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@SuppressWarnings("rawtypes")
	public static ResponseEntity errorsToResponse(List<String> errors) {
		ErrorResponse<String> errorResponse = new ErrorResponse<String>();
		errors.forEach(e -> errorResponse.addErrors(e));
		return ResponseEntity.badRequest().body(errorResponse);
	}
}