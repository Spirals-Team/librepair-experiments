package net.coljate.set.impl;

import java.util.Iterator;
import java.util.Objects;

import net.coljate.set.AbstractSet;
import net.coljate.set.MutableSet;
import net.coljate.set.Set;

/**
 *
 * @author Ollie
 */
public class UnmodifiableSet<T> extends AbstractSet<T> {

    @SuppressWarnings("unchecked")
    public static <T> Set<T> viewOf(final Set<? extends T> set) {
        return set instanceof MutableSet
                ? new UnmodifiableSet<>(set)
                : (Set<T>) set;
    }

    private final Set<? extends T> delegate;

    protected UnmodifiableSet(final Set<? extends T> delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }

    @Override
    public boolean contains(final Object object) {
        return delegate.contains(object);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterator<T> iterator() {
        return (Iterator<T>) delegate.iterator();
    }

    @Override
    protected boolean equals(final Set<?> that) {
        return that instanceof UnmodifiableSet
                && Objects.equals(delegate, ((UnmodifiableSet) that).delegate);
    }

}
