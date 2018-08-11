package net.coljate.map;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import net.coljate.set.Set;

/**
 *
 * @author Ollie
 * @see <a href="https://en.wikipedia.org/wiki/Bijection">Bijection</a>
 */
public interface BidirectionalMap<K, V> extends Map<K, V> {

    @Override
    Entry<K, V> getEntry(Object key);

    @Nonnull
    BidirectionalMap<V, K> inverseView();

    @CheckForNull
    default Entry<V, K> getInverseEntry(final Object value) {
        return this.inverseView().getEntry(value);
    }

    @Override
    default Set<V> values() {
        return this.inverseView().keys();
    }

    @Override
    MutableBidirectionalMap<K, V> mutableCopy();

}
