package net.coljate.cache;

import net.coljate.map.ConcurrentSortedMap;

/**
 *
 * @author ollie
 */
public interface ConcurrentSortedCache<K, V> extends SortedCache<K, V>, ConcurrentSortedMap<K, V>  {
    
}
