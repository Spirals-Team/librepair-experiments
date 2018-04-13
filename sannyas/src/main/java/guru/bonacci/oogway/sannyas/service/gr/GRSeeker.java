package guru.bonacci.oogway.sannyas.service.gr;


import static java.util.Arrays.asList;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannyas.service.filters.LengthFilter;
import guru.bonacci.oogway.sannyas.service.general.Sannyasin;
import guru.bonacci.oogway.sannyas.service.steps.CharacterGuardian;
import guru.bonacci.oogway.sannyas.service.steps.KeyPhraser;

/**
 * I like GoodReads! It was the first Sannyasin.
 */
@Component
public class GRSeeker implements Sannyasin {

	@Autowired
	private CharacterGuardian characterGuardian;

	@Autowired
	private KeyPhraser keyPhraser;

	@Autowired
	private LengthFilter lengthFilter;

	@Autowired
	private GRScraper scraper;

	@Override
	public List<Function<String,String>> preprocessingSteps() {
		return asList(characterGuardian, keyPhraser);
	}

	@Override
	public List<Predicate<String>> postfilteringStep() {
		return asList(lengthFilter);
	}

	@Override
	public GRScraper getScraper() {
		return scraper;
	};
}
