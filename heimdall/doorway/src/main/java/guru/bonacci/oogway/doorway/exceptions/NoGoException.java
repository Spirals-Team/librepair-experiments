package guru.bonacci.oogway.doorway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason="What do you think you're doing?")
public class NoGoException extends RuntimeException {

	private static final long serialVersionUID = 7082274870170885633L;
}
