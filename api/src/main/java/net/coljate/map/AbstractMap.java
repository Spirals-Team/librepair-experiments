package net.coljate.map;

import net.coljate.collection.AbstractCollection;
import net.coljate.collection.Collection;
import net.coljate.util.Hashing;

/**
 *
 * @author Ollie
 */
public abstract class AbstractMap<K, V>
        extends AbstractCollection<Entry<K, V>>
        implements Map<K, V> {

    @Override
    @SuppressWarnings("override.param.invalid")
    public boolean equals(final Object obj) {
        return super.equals(obj);
    }

    @Override
    protected boolean equals(final Collection<?> that) {
        return that instanceof Map
                && this.equals((Map) that);
    }

    protected boolean equals(final Map<?, ?> that) {
        return this.getClass() == that.getClass()
                && this.elementsEqual(that);
    }

    @Override
    public int hashCode() {
        return Hashing.unorderedHash(this);
    }

    @Override
    public String toString() {
        return Map.toString(this);
    }

}
