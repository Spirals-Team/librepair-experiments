package net.coljate.list;

import net.coljate.list.impl.WrappedListIterator;

import java.util.NoSuchElementException;

import javax.annotation.Nonnull;

import net.coljate.util.iterator.UnmodifiableIterator;

/**
 *
 * @author Ollie
 */
public interface ImmutableListIterator<T> extends ListIterator<T>, UnmodifiableIterator<T> {

    @SuppressWarnings("unchecked")
    static <T> ImmutableListIterator<T> empty() {
        return EmptyImmutableListIterator.INSTANCE;
    }

    static <T> ImmutableListIterator<T> of(final java.util.ListIterator<? extends T> iterator) {
        return new WrappedImmutableListIterator<>(iterator);
    }

    @SuppressWarnings("rawtypes")
    class EmptyImmutableListIterator<T> extends EmptyUnmodifiableIterator<T> implements ImmutableListIterator<T> {

        static final EmptyImmutableListIterator INSTANCE = new EmptyImmutableListIterator();

        protected EmptyImmutableListIterator() {
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public T previous() {
            throw new NoSuchElementException();
        }

    };

    class WrappedImmutableListIterator<T> extends WrappedListIterator<T> implements ImmutableListIterator<T> {

        public WrappedImmutableListIterator(@Nonnull final java.util.ListIterator<? extends T> iterator) {
            super(iterator);
        }

    }

}
