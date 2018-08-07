package guru.bonacci.oogway.sannyas.service.general;

import static org.apache.commons.lang.StringUtils.split;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import guru.bonacci.oogway.shareddomain.GemCarrier;

/**
 * Sannyasin: Seeker for Truth (is basically a Worker).
 * 
 * Sannyas means courage more than anything else, because it is a declaration of
 * your individuality, a declaration of freedom, a declaration that you will not
 * be any more part of the mob madness, the mob psychology. It is a declaration
 * that you are becoming universal; you will not belong to any country, to any
 * church, to any race, to any religion.”
 */
public interface Sannyasin {

	List<Function<String,String>> preprocessingSteps();

	default List<GemCarrier> seek(String tagsAsString) {
		String[] tags = split(tagsAsString);
		return getScraper().find(tags);
	}

	Scraper getScraper();

	List<Predicate<String>> postfilteringStep();
}
