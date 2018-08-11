package net.coljate.sequence;

import net.coljate.collection.Collection;
import net.coljate.list.Array;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.function.BooleanSupplier;

/**
 * Lightweight list-like sequence.
 *
 * @param <T>
 * @see net.coljate.list.List
 */
public interface FiniteSequence<T> extends Sequence<T>, Collection<T> {

    @CheckForNull
    @Override
    default T first() {
        return Collection.super.first();
    }

    @Nonnull
    @Override
    default Array<T> arrayCopy(final int length) {
        return Sequence.super.arrayCopy(length);
    }

    @Nonnull
    @Override
    default Iterator<T> iterator(final BooleanSupplier iterateUntil) {
        return new Iterator<T>() {

            final Iterator<T> delegate = FiniteSequence.this.iterator();

            @Override
            public boolean hasNext() {
                return iterateUntil.getAsBoolean() && delegate.hasNext();
            }

            @Override
            public T next() {
                return delegate.next();
            }

        };
    }

}
