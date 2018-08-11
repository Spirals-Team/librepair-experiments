package net.coljate.cache;

import net.coljate.list.List;

/**
 *
 * @author Ollie
 */
public interface ListMultimap<K, V>
        extends Multimap<K, V> {

    @Override
    List<V> get(K key);

    @Override
    MultimapEntry<K, V, ? extends List<V>> getEntry(final Object key);

    @Override
    MutableListMultimap<K, V> mutableCopy();

    @Override
    ImmutableListMultimap<K, V> immutableCopy();

}
