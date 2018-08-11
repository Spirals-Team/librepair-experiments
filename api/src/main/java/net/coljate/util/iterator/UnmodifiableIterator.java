package net.coljate.util.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author Ollie
 */
public interface UnmodifiableIterator<T> extends Iterator<T> {

    @SuppressWarnings("unchecked")
    static <T> UnmodifiableIterator<T> wrap(final Iterator<? extends T> iterator) {
        return iterator instanceof UnmodifiableIterator
                ? (UnmodifiableIterator) iterator
                : new DelegatedUnmodifiableIterator<>(iterator);
    }

    @SuppressWarnings("unchecked")
    static <T> UnmodifiableIterator<T> of() {
        return EmptyUnmodifiableIterator.INSTANCE;
    }

    static <T> UnmodifiableIterator<T> of(final T element) {
        return new UnmodifiableSingletonIterator<>(element);
    }

    @Override
    @Deprecated
    default void remove() {
        throw new UnsupportedOperationException("Unmodifiable");
    }

    class DelegatedUnmodifiableIterator<T> implements UnmodifiableIterator<T> {

        private final Iterator<? extends T> delegate;

        DelegatedUnmodifiableIterator(Iterator<? extends T> delegate) {
            this.delegate = delegate;
        }

        @Override
        public boolean hasNext() {
            return delegate.hasNext();
        }

        @Override
        public T next() {
            return delegate.next();
        }

    }

    class EmptyUnmodifiableIterator<T> implements UnmodifiableIterator<T> {

        static final EmptyUnmodifiableIterator INSTANCE = new EmptyUnmodifiableIterator();

        protected EmptyUnmodifiableIterator() {
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            throw new NoSuchElementException();
        }

    }

    class UnmodifiableSingletonIterator<T> implements UnmodifiableIterator<T> {

        private final T element;
        private boolean done;

        protected UnmodifiableSingletonIterator(final T element) {
            this.element = element;
        }

        @Override
        public boolean hasNext() {
            return !done;
        }

        @Override
        public T next() {
            if (done) {
                throw new NoSuchElementException();
            }
            done = true;
            return element;
        }

    }

}
