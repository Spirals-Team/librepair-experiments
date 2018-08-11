package net.coljate.set;

import net.coljate.sequence.FiniteSequence;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.BooleanSupplier;

/**
 * @author Ollie
 */
public interface SequentialSet<T> extends FiniteSequence<T>, Set<T> {

    @Override
    default T first() {
        return FiniteSequence.super.first();
    }

    T last();

    @Nonnull
    default Optional<SortedSet<T>> toSortedSet() {
        return this instanceof SortedSet
                ? Optional.of((SortedSet<T>) this)
                : Optional.empty();
    }

    @Nonnull
    @Override
    default Iterator<T> iterator(BooleanSupplier iterateUntil) {
        throw new UnsupportedOperationException();
    }

}
