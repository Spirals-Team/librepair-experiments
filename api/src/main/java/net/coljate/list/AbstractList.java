package net.coljate.list;

import net.coljate.collection.AbstractCollection;
import net.coljate.collection.Collection;
import net.coljate.util.Hashing;

/**
 *
 * @author Ollie
 */
public abstract class AbstractList<T>
        extends AbstractCollection<T>
        implements List<T> {

    @Override
    public boolean equals(final Object that) {
        return super.equals(that);
    }

    @Override
    protected boolean equals(final Collection<?> that) {
        return that instanceof List
                && this.equals((List<?>) that);
    }

    protected boolean equals(final List<?> that) {
        return this.elementsEqual(that);
    }

    @Override
    public int hashCode() {
        return Hashing.orderedHash(this);
    }

    @Override
    public ImmutableList<T> immutableCopy() {
        return List.super.immutableCopy();
    }

    @Override
    public MutableList<T> mutableCopy() {
        return List.super.mutableCopy();
    }

}
