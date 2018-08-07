package guru.bonacci.oogway.sannyas.service.steps;

import static java.util.stream.Collectors.joining;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.simple.SentenceAlgorithms;

/**
 * Determines the important words from a phrase
 * 
 * Phrases or a search term that is made up of multiple keywords, or a specific
 * combination of keywords, that a user would enter into a search engine. In SEO
 * (search engine optimization), optimizing your site for specific keyphrases
 * will yield a smaller number of more specific and relevant traffic.
 */
@Component
public class KeyPhraser implements Function<String,String> {

	private final Logger logger = getLogger(this.getClass());

	@Override
	public String apply(String input) {
		logger.debug("in: " + input);
		if (input == null) {
			throw new IllegalStateException("for test purposes only");
		}
		
		Sentence sentence = new Sentence(input);
		SentenceAlgorithms algorithms = new SentenceAlgorithms(sentence);
		List<String> keyphrases = algorithms.keyphrases();
		String output = keyphrases.stream().collect(joining(" "));
		
		logger.debug("out: " + output);
		return output;
	}
}
