package net.coljate.set.impl;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

import net.coljate.collection.impl.SingletonCollection;
import net.coljate.set.ImmutableSet;
import net.coljate.set.MutableSet;
import net.coljate.set.SequentialSet;
import net.coljate.set.Set;

/**
 *
 * @author Ollie
 */
public class SingletonSet<T>
        extends SingletonCollection<T>
        implements ImmutableSet<T>, SequentialSet<T>, Serializable {

    private static final long serialVersionUID = 1L;

    public static <T> SingletonSet<T> of(final T element) {
        return new SingletonSet<>(element);
    }

    @SafeVarargs
    public static <T> Optional<SingletonSet<T>> of(final T... elements) {
        switch (elements.length) {
            case 0:
                return Optional.empty();
            case 1:
                return Optional.of(of(elements[0]));
            default:
                final T first = elements[0];
                for (int i = 1; i < elements.length; i++) {
                    if (!Objects.equals(first, elements[i])) {
                        return Optional.empty();
                    }
                }
                return Optional.of(of(first));
        }
    }

    protected SingletonSet(final T element) {
        super(element);
    }

    @Override
    public T last() {
        return this.element();
    }

    @Override
    public ImmutableSet<T> with(final T element) {
        return this.contains(element)
                ? this
                : new TwoSet<>(this.element(), element);
    }

    @Override
    public MutableSet<T> mutableCopy() {
        return ImmutableSet.super.mutableCopy();
    }

    @Override
    @Deprecated
    public SingletonSet<T> immutableCopy() {
        return this;
    }

    @Override
    public boolean equals(final Object that) {
        return that instanceof SingletonSet
                && this.elementsEqual((Set) that);
    }

    @Override
    public int hashCode() {
        return Set.elementHash(this);
    }

}
