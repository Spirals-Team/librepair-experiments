package net.coljate.map;

/**
 *
 * @author ollie
 */
public interface ConcurrentSortedMap<K, V> extends MutableSortedMap<K, V>, ConcurrentMap<K, V> {

    @Override
    ConcurrentSortedMap<K, V> mutableCopy();

}
