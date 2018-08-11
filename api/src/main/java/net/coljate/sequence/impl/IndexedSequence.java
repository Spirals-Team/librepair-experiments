package net.coljate.sequence.impl;

import net.coljate.sequence.Sequence;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.IntFunction;

public class IndexedSequence<T> implements Sequence<T> {

    private final IntFunction<? extends T> generator;

    public IndexedSequence(@Nonnull final IntFunction<? extends T> generator) {
        this.generator = Objects.requireNonNull(generator);
    }

    @CheckForNull
    @Override
    public T first() {
        return generator.apply(0);
    }

    public T at(final int index) {
        return generator.apply(index);
    }

    @Nonnull
    @Override
    public Iterator<T> iterator(final BooleanSupplier iterateUntil) {
        return new Iterator<T>() {

            private int index = 0;

            @Override
            public boolean hasNext() {
                return iterateUntil.getAsBoolean();
            }

            @Override
            public T next() {
                return generator.apply(index++);
            }

        };
    }

}
