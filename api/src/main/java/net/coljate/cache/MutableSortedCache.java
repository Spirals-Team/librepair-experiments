package net.coljate.cache;

import net.coljate.map.MutableSortedMap;

/**
 *
 * @author ollie
 */
public interface MutableSortedCache<K, V> extends SortedCache<K, V>, MutableSortedMap<K, V> {

    @Override
    MutableSortedCache<K, V> mutableCopy();

}
