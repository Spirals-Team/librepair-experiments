package guru.bonacci.oogway.sannyas.service.steps;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.function.Function;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Removes...
 */
@Component
public class PunctuationMarkDestroyer implements Function<String,String> {

	private final Logger logger = getLogger(this.getClass());

	@Override
	public String apply(String input) {
		logger.debug("in: " + input);

	    String output = input;
	    
		logger.debug("out: " + output);
		return output;
	}
}
