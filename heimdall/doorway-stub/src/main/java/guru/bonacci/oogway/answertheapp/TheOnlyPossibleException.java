package guru.bonacci.oogway.answertheapp;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, 
				reason="Nice people always make me want to do bad things.")
public class TheOnlyPossibleException extends RuntimeException {

	private static final long serialVersionUID = 6871971296612014562L;
}
