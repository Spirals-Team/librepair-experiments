package net.coljate.cache;

import net.coljate.collection.Collection;
import net.coljate.collection.ConcurrentCollection;

/**
 *
 * @author Ollie
 */
public interface ConcurrentMultimap<K, V>
        extends ConcurrentCache<K, Collection<V>>, MutableMultimap<K, V> {

    @Override
    ConcurrentCollection<V> get(K key);

    @Override
    MutableMultimapEntry<K, V, ? extends ConcurrentCollection<V>> getEntry(Object key);

    @Override
    ConcurrentMultimap<K, V> mutableCopy();

}
