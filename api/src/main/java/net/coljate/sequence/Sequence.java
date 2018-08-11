package net.coljate.sequence;

import net.coljate.list.Array;
import net.coljate.list.List;
import net.coljate.list.MutableArray;
import net.coljate.sequence.impl.IndexedSequence;
import net.coljate.util.complexity.Complexity;
import net.coljate.util.complexity.TimeComplexity;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.function.BooleanSupplier;
import java.util.function.IntFunction;

/**
 * Has a specific order following a definite {@link #first first} element.
 * <p>
 * May be {@link List finite} or infinite.
 *
 * @author Ollie
 * @since 1.0
 * @see Array
 */
public interface Sequence<T> {

    /**
     * @return the first element in this ordered collection, or {@code null} if empty.
     */
    @TimeComplexity(Complexity.CONSTANT)
    @CheckForNull
    T first();

    @Nonnull
    Iterator<T> iterator(BooleanSupplier iterateUntil);

    @Nonnull
    default Array<T> arrayCopy(final int length) {
        final MutableArray<T> array = MutableArray.create(length);
        final Iterator<T> iterator = this.iterator(() -> true);
        for (int i = 0; i < length; i++) {
            array.set(i, iterator.hasNext() ? iterator.next() : null);
        }
        return array;
    }

    static <T> Sequence<T> indexed(@Nonnull final IntFunction<? extends T> generator) {
        return new IndexedSequence<>(generator);
    }

}
