package net.coljate.set.impl;

import java.util.Spliterator;
import java.util.function.Function;
import java.util.function.Predicate;

import net.coljate.collection.Empty;
import net.coljate.set.AbstractSet;
import net.coljate.set.ImmutableSet;
import net.coljate.set.Set;

/**
 *
 * @author Ollie
 */
public class EmptySet<T>
        extends AbstractSet<T>
        implements Empty<T>, ImmutableSet<T> {

    private static final long serialVersionUID = 1L;
    private static final EmptySet INSTANCE = new EmptySet();

    @SuppressWarnings("unchecked")
    public static <T> EmptySet<T> instance() {
        return INSTANCE;
    }

    protected EmptySet() {
    }

    @Override
    public boolean contains(final Object object) {
        return Empty.super.contains(object);
    }

    @Override
    protected boolean equals(final Set<?> that) {
        return that instanceof EmptySet;
    }

    @Override
    public SingletonSet<T> with(final T element) {
        return SingletonSet.of(element);
    }

    @Override
    public Set<T> xor(final Set<? extends T> that) {
        return Set.unmodifiable(that);
    }

    @Override
    public Set<T> union(final Set<? extends T> that) {
        return Set.unmodifiable(that);
    }

    @Override
    public EmptySet<T> not(final Set<? extends T> that) {
        return this;
    }

    @Override
    public EmptySet<T> intersection(final Set<? extends T> that) {
        return this;
    }

    @Override
    public EmptySet<T> filter(final Predicate<? super T> predicate) {
        return this;
    }

    @Override
    public <R> Empty<R> transform(final Function<? super T, ? extends R> transformation) {
        return instance();
    }

    @Override
    public Spliterator<T> spliterator() {
        return Empty.super.spliterator();
    }

}
