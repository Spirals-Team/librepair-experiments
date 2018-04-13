package guru.bonacci.oogway.doorway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.TOO_MANY_REQUESTS, 
				reason="Greed is a sin against God, just as all mortal sins, in as much as man condemns things eternal for the sake of temporal things")
public class GreedyException extends RuntimeException {

	private static final long serialVersionUID = 6871971296612014562L;
}
