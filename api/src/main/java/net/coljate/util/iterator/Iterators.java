package net.coljate.util.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 *
 * @author Ollie
 */
public class Iterators {

    public static <B, T extends B> UnmodifiableCovariantIterator<B, T> empty() {
        return UnmodifiableCovariantIterator.of();
    }

    public static <B1, B2, T extends B2> CovariantIterator<B2, T> transform(final Iterator<? extends B1> from, final Function<? super B1, ? extends T> transform) {
        return new CovariantIterator<B2, T>() {

            @Override
            public boolean hasNext() {
                return from.hasNext();
            }

            @Override
            public T next() {
                return transform.apply(from.next());
            }

            @Override
            public void remove() {
                from.remove();
            }

        };
    }

    public static <B1, B2, T extends B2> UnmodifiableCovariantIterator<B2, T> transform(final UnmodifiableIterator<? extends B1> from, final Function<? super B1, ? extends T> transform) {
        return new UnmodifiableCovariantIterator<B2, T>() {

            @Override
            public boolean hasNext() {
                return from.hasNext();
            }

            @Override
            public T next() {
                return transform.apply(from.next());
            }

        };
    }

    public static <B, T extends B> CovariantIterator<B, T> filter(final Iterator<? extends T> iterator, final Predicate<? super T> predicate) {
        return filter(iterator::hasNext, iterator::next, predicate);
    }

    public static <B, T extends B> CovariantIterator<B, T> filter(
            final BooleanSupplier canGenerateCandidate,
            final Supplier<? extends T> generateCandidate,
            final Predicate<? super T> isValid) {
        return new FilteredIterator<B, T>() {

            @Override
            protected boolean canGenerateCandidate() {
                return canGenerateCandidate.getAsBoolean();
            }

            @Override
            protected T generateCandidate() {
                return generateCandidate.get();
            }

            @Override
            protected boolean isValid(final T candidate) {
                return isValid.test(candidate);
            }

        };
    }

    public static int count(final Iterator<?> iterator) {
        int count = 0;
        while (iterator.hasNext()) {
            count++;
        }
        return count;
    }

    public static <T> Iterator<T> first(final Iterator<? extends T> iterator, final int total) {
        if (total == 0) {
            return empty();
        }
        return new Iterator<T>() {

            int current = 0;

            @Override
            public boolean hasNext() {
                return iterator.hasNext() && current < total;
            }

            @Override
            public T next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                current += 1;
                return iterator.next();
            }

            @Override
            public void remove() {
                iterator.remove();
            }

        };
    }

    public static <T> T first(final Iterator<? extends T> iterator) {
        return iterator.hasNext()
                ? iterator.next()
                : null;
    }

    public static <T> T last(final Iterator<? extends T> iterator) {
        T last = null;
        while (iterator.hasNext()) {
            last = iterator.next();
        }
        return last;
    }

    @SuppressWarnings("unchecked")
    public static <T> Iterator<T> covariant(final Iterator<? extends T> iterator) {
        return (Iterator<T>) iterator;
    }

    @SafeVarargs
    public static <T> Iterator<T> concat(final Iterator<? extends T>... iterators) {
        if (iterators.length == 0) {
            return empty();
        }
        return new Iterator<T>() {

            int index = 0;
            Iterator<? extends T> current = iterators[0];

            @Override
            public boolean hasNext() {
                while (index < iterators.length - 1 && !current.hasNext()) {
                    current = iterators[++index];
                }
                return current.hasNext();
            }

            @Override
            public T next() {
                return current.next();
            }

            @Override
            public void remove() {
                current.remove();
            }

        };
    }

    public static abstract class FilteredIterator<B, T extends B> implements CovariantIterator<B, T> {

        private boolean hasNext;
        private T next;

        protected abstract boolean canGenerateCandidate();

        protected abstract T generateCandidate();

        protected abstract boolean isValid(T candidate);

        @Override
        public boolean hasNext() {
            if (hasNext) {
                return true;
            }
            while (this.canGenerateCandidate()) {
                final T candidate = this.generateCandidate();
                if (this.isValid(candidate)) {
                    next = candidate;
                    hasNext = true;
                    break;
                }
            }
            return hasNext;
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            hasNext = false;
            return next;
        }

    }

    public static <T> UnmodifiableIterator<T> of(final T element) {
        return UnmodifiableIterator.of(element);
    }

    public static <T> Iterator<T> of(final T[] array) {
        return new Iterator<T>() {

            private int index;

            @Override
            public boolean hasNext() {
                return index < array.length;
            }

            @Override
            public T next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return array[index++];
            }

        };
    }

}
