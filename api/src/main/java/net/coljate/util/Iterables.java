package net.coljate.util;

import java.util.Comparator;
import java.util.OptionalInt;

import net.coljate.IterableExtension;
import net.coljate.collection.Collection;
import net.coljate.util.iterator.Iterators;

/**
 *
 * @author Ollie
 */
public class Iterables {

    public static OptionalInt maybeCount(final Iterable<?> iterable) {
        if (iterable instanceof Collection) {
            return OptionalInt.of(((Collection) iterable).count());
        }
        if (iterable instanceof java.util.Collection) {
            return OptionalInt.of(((java.util.Collection) iterable).size());
        }
        return OptionalInt.empty();
    }

    public static int count(final Iterable<?> iterable) {
        if (iterable instanceof Collection) {
            return ((IterableExtension) iterable).count();
        }
        if (iterable instanceof java.util.Collection) {
            return ((java.util.Collection) iterable).size();
        }
        return Iterators.count(iterable.iterator());
    }

    public static <T> T last(final Iterable<T> iterable) {
        return Iterators.last(iterable.iterator());
    }

    public static <T> T least(final Iterable<? extends T> iterable, final Comparator<? super T> comparator) {
        T least = null;
        for (final T element : iterable) {
            if (least == null) {
                least = element;
            } else if (comparator.compare(element, least) < 0) {
                least = element;
            }
        }
        return least;
    }

    public static <T> T greatest(final Iterable<? extends T> iterable, final Comparator<? super T> comparator) {
        T greatest = null;
        for (final T element : iterable) {
            if (greatest == null) {
                greatest = element;
            } else if (comparator.compare(element, greatest) > 0) {
                greatest = element;
            }
        }
        return greatest;
    }

}
