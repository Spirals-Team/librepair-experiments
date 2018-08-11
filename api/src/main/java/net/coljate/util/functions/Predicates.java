package net.coljate.util.functions;

import java.util.Comparator;
import java.util.function.Predicate;

/**
 *
 * @author Ollie
 */
public class Predicates {

    public static <T> Predicate<T> greaterThan(final T element, final boolean orEqual, final Comparator<? super T> comparator) {
        return e -> {
            final int c = comparator.compare(e, element);
            return c > 0 || (c == 0 && orEqual);
        };
    }

    public static <T> Predicate<T> lessThan(final T element, final boolean orEqual, final Comparator<? super T> comparator) {
        return e -> {
            final int c = comparator.compare(e, element);
            return c < 0 || (c == 0 && orEqual);
        };
    }

}
