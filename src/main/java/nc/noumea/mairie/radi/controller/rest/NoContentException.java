package nc.noumea.mairie.radi.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoContentException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7130734332744574866L;

}
