package guru.bonacci.oogway.sannyas.service.steps;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.function.Function;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Keeps letters, numbers and spaces
 */
@Component
public class CharacterGuardian implements Function<String,String> {

	private final Logger logger = getLogger(this.getClass());

	@Override
	public String apply(String input) {
		logger.debug("in: " + input);

		String output = input.replaceAll("[^\\w\\s]","");
	    
		logger.debug("out: " + output);
		return output;
	}
}
