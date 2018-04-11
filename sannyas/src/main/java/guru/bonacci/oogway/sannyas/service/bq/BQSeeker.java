package guru.bonacci.oogway.sannyas.service.bq;


import static java.util.Arrays.asList;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannyas.service.filters.LengthFilter;
import guru.bonacci.oogway.sannyas.service.general.Sannyasin;
import guru.bonacci.oogway.sannyas.service.steps.CharacterGuardian;
import guru.bonacci.oogway.sannyas.service.steps.KeyPhraser;
import guru.bonacci.oogway.sannyas.service.steps.Lemmatizor;

/**
 * They say: Share our extensive collection of famous quotes by authors,
 * celebrities, newsmakers, and more. Enjoy our Quotes of the Day on the web,
 * Facebook, and blogs.
 */
@Component
//blocks requests from Tor-relays
@ConditionalOnProperty(name = "proxy.enabled", havingValue = "false")
public class BQSeeker implements Sannyasin {

	@Autowired
	private CharacterGuardian characterGuardian;

	@Autowired
	private KeyPhraser keyPhraser;

	@Autowired
	private Lemmatizor lemmatizor;

	@Autowired
	private LengthFilter lengthFilter;

	@Autowired
	private BQScraper scraper;

	@Override
	public List<Function<String, String>> preprocessingSteps() {
		return asList(characterGuardian, keyPhraser, lemmatizor);
	}

	@Override
	public List<Predicate<String>> postfilteringStep() {
		return asList(lengthFilter);
	}

	@Override
	public BQScraper getScraper() {
		return scraper;
	};
}
