package guru.bonacci.oogway.sannyas.service.steps;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang.StringUtils.split;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Function;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Removes duplicate words
 */
@Component
public class DuplicateRemover implements Function<String,String> {

	private final Logger logger = getLogger(this.getClass());

	@Override
	public String apply(String input) {
		logger.debug("in: " + input);

		String[] words = split(input);
		Set<String> uniqueWords = new LinkedHashSet<>();
	    Collections.addAll(uniqueWords, words);
	    String output = uniqueWords.stream().collect(joining(" "));
	    
		logger.debug("out: " + output);
		return output;
	}
}
