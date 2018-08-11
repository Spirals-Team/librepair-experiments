package net.coljate.set;

import net.coljate.collection.AbstractCollection;
import net.coljate.collection.Collection;
import net.coljate.util.Hashing;

/**
 *
 * @author Ollie
 */
public abstract class AbstractSet<T>
        extends AbstractCollection<T>
        implements Set<T> {

    @Override
    public abstract boolean contains(Object object);

    @Override
    public boolean equals(final Object that) {
        return super.equals(that);
    }

    @Override
    protected boolean equals(final Collection<?> that) {
        return that instanceof Set
                && this.equals((Set) that);
    }

    protected boolean equals(final Set<?> that) {
        return this.elementsEqual(that);
    }

    @Override
    public int hashCode() {
        return Hashing.unorderedHash(this);
    }

}
