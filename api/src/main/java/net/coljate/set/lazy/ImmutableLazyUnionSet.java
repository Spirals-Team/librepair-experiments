package net.coljate.set.lazy;

import java.util.Objects;

import net.coljate.util.iterator.UnmodifiableIterator;
import net.coljate.set.ImmutableSet;
import net.coljate.set.MutableSet;

/**
 *
 * @author Ollie
 */
public class ImmutableLazyUnionSet<T>
        extends LazySetUnion<T>
        implements ImmutableSet<T> {

    @SuppressWarnings("unchecked")
    public static <T> ImmutableSet<T> of(final ImmutableSet<T> s1, final ImmutableSet<? extends T> s2) {
        if (s1.isEmpty()) {
            return (ImmutableSet<T>) s2;
        } else if (s2.isEmpty()) {
            return s1;
        } else {
            return new ImmutableLazyUnionSet<>(s1, s2);
        }
    }

    private final ImmutableSet<T> s1;
    private final ImmutableSet<? extends T> s2;

    protected ImmutableLazyUnionSet(final ImmutableSet<T> s1, ImmutableSet<? extends T> s2) {
        super(s1, s2);
        this.s1 = Objects.requireNonNull(s1);
        this.s2 = Objects.requireNonNull(s2);
    }

    @Override
    public UnmodifiableIterator<T> iterator() {
        return UnmodifiableIterator.wrap(super.iterator());
    }

    @Override
    public ImmutableLazyUnionSet<T> with(final T element) {
        return this.contains(element)
                ? this
                : new ImmutableLazyUnionSet<>(s1.with(element), s2);
    }

    @Override
    public MutableSet<T> mutableCopy() {
        return super.mutableCopy();
    }

    @Override
    @Deprecated
    public ImmutableLazyUnionSet<T> immutableCopy() {
        return this;
    }

}
