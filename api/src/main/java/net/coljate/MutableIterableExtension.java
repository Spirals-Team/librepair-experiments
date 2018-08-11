package net.coljate;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import net.coljate.util.Integers;

/**
 *
 * @author Ollie
 * @since 1.0
 */
public interface MutableIterableExtension<T> extends Iterable<T> {

    default boolean removeFirst(final Object element) {
        return this.removeN(element, 1) > 0;
    }

    default int removeAll(final Object element) {
        return this.removeN(element, Integer.MAX_VALUE);
    }

    default int removeN(final Object element, @Nonnegative final int max) {
        if (max == 0) {
            return 0;
        }
        Integers.requirePositive(max, i -> "Max must be non-negative but was " + i + "!");
        int removed = 0;
        for (final Iterator<T> iterator = this.iterator(); iterator.hasNext();) {
            if (Objects.equals(iterator.next(), element)) {
                iterator.remove();
                if (++removed >= max) {
                    break;
                }
            }
        }
        return removed;
    }

    default boolean removeAll(@Nonnull final Iterable<?> elements) {
        boolean removed = false;
        for (final Object element : elements) {
            removed = this.removeAll(element) > 0 || removed;
        }
        return removed;
    }

    default int removeWhere(@Nonnull final Predicate<? super T> predicate) {
        int removed = 0;
        for (final Iterator<T> iterator = this.iterator(); iterator.hasNext();) {
            if (predicate.test(iterator.next())) {
                iterator.remove();
                removed++;
            }
        }
        return removed;
    }

    default void clear() {
        for (final Iterator<?> iterator = this.iterator(); iterator.hasNext();) {
            iterator.next();
            iterator.remove();
        }
    }

}
