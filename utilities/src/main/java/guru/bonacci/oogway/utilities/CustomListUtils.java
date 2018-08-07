package guru.bonacci.oogway.utilities;

import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * This class does not belong here. 
 * In fact, it does not belong anywhere. 
 * It should not even exist. 
 * Someone else should have written a proper getRandom function in a famous library.
 * However, I couldn't find one.
 * That's why it is here.
 */
public class CustomListUtils {

	/**
	 * Random object used by random method. This has to be not local to the
	 * random method so as to not return the same value in the same millisecond.
	 */
	private static final Random RANDOM = new Random();

	public static <E> Optional<E> random(final List<E> collection) {
		return random(collection, RANDOM);
	}

	private static <E> Optional<E> random(final List<E> list, Random random) {
		return Optional.ofNullable(isEmpty(list) ? null : list.get(random.nextInt(list.size())));
	}
}
