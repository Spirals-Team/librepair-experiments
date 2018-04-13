package guru.bonacci.oogway.doorway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Our fault, sorry.")
public class OurFaultException extends RuntimeException {

	private static final long serialVersionUID = -1799407745424650965L;
}
