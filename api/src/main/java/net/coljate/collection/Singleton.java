package net.coljate.collection;

import net.coljate.sequence.FiniteSequence;

import java.util.Objects;

/**
 *
 * @author Ollie
 */
public interface Singleton<T>
        extends ImmutableCollection<T>, FiniteSequence<T> {

    T element();

    @Override
    default boolean contains(final Object object) {
        return Objects.equals(object, this.element());
    }

    @Override
    default int count() {
        return 1;
    }

    @Override
    @Deprecated
    default T first() {
        return this.element();
    }

}
