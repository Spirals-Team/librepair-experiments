package net.coljate.util.iterator;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * An iterator that allows generic declaration of a base type {@code <T>} but can be covariantly subclassed as some
 * {@code <R extends T>} on the elements returned by {@link #next next()}.
 *
 * @author Ollie
 * @param <B> base (inherited) type
 * @param <T> {@link #next} type
 */
public interface CovariantIterator<B, T extends B> extends Iterator<B> {

    @Override
    T next();

    default T first(final Predicate<? super T> predicate) {
        while (this.hasNext()) {
            final T next = this.next();
            if (predicate.test(next)) {
                return next;
            }
        }
        return null;
    }

    default <T2, R2 extends T2> CovariantIterator<T2, R2> transform(final Function<? super B, ? extends R2> transformation) {
        return Iterators.transform(this, transformation);
    }

    static <T, R extends T> CovariantIterator<T, R> of(final Iterator<R> iterator) {
        return new CovariantIterator<T, R>() {

            @Override
            public R next() {
                return iterator.next();
            }

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public void remove() {
                iterator.remove();
            }

        };
    }

}
