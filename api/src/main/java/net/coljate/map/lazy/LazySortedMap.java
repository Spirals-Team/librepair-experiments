package net.coljate.map.lazy;

import net.coljate.map.MutableSortedMap;
import net.coljate.map.SortedMap;

/**
 *
 * @author ollie
 */
public interface LazySortedMap<K, V> extends LazyMap<K, V>, SortedMap<K, V> {

    @Override
    default MutableSortedMap<K, V> mutableCopy() {
        return SortedMap.super.mutableCopy();
    }

}
