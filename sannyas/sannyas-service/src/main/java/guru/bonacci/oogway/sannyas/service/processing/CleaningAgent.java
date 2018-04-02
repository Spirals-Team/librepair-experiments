package guru.bonacci.oogway.sannyas.service.processing;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannyas.service.filters.ProfanityFilter;
import guru.bonacci.oogway.sannyas.service.general.Sannyasin;
import guru.bonacci.oogway.shareddomain.GemCarrier;

/**
 * “Every aspect of your life is anchored energetically in your living space, so
 * clearing clutter can completely transform your entire existence.” 
 * - Karen Kingston
 */
@Component
public class CleaningAgent {

	@Autowired
	private ProfanityFilter profanityFilter;

	public List<GemCarrier> noMoreClutter(Sannyasin sannya, List<GemCarrier> found) {
		Predicate<String> postfiltering = sannya.postfilteringStep().stream()
															  		.reduce(p -> true, Predicate::and);		
		return found.stream()
			 .filter(gem -> postfiltering.test(gem.getSaying()))
			 .filter(gem -> profanityFilter.test(gem.getSaying()))
			 .collect(toList());
	}
}
