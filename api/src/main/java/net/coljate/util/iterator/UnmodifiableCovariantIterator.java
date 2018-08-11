package net.coljate.util.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 *
 * @author Ollie
 */
public interface UnmodifiableCovariantIterator<T, R extends T>
        extends UnmodifiableIterator<T>, CovariantIterator<T, R> {

    @SuppressWarnings("unchecked")
    static <T, R extends T> UnmodifiableCovariantIterator<T, R> of() {
        return EmptyUnmodifiableCovariantIterator.INSTANCE;
    }

    static <T, R extends T> UnmodifiableCovariantIterator<T, R> wrap(final Iterator<R> iterator) {
        return new UnmodifiableCovariantIterator<T, R>() {

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public R next() {
                return iterator.next();
            }

        };
    }

    static <T, R extends T> UnmodifiableCovariantIterator<T, R> wrap(final Iterator<T> iterator, final Function<? super T, ? extends R> transform) {
        return new UnmodifiableCovariantIterator<T, R>() {

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public R next() {
                return transform.apply(iterator.next());
            }

        };
    }

    static <T, R extends T> UnmodifiableCovariantIterator<T, R> wrap(final CovariantIterator<T, R> iterator) {
        return new UnmodifiableCovariantIterator<T, R>() {

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public R next() {
                return iterator.next();
            }

        };
    }

    class EmptyUnmodifiableCovariantIterator<T, R extends T>
            extends EmptyUnmodifiableIterator<T>
            implements UnmodifiableCovariantIterator<T, R> {

        static final EmptyUnmodifiableCovariantIterator INSTANCE = new EmptyUnmodifiableCovariantIterator();

        @Override
        public R next() {
            throw new NoSuchElementException("Empty iterator");
        }

    }

}
