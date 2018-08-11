package net.coljate.cache;

import net.coljate.collection.Collection;
import net.coljate.map.ImmutableMap;
import net.coljate.map.Map;
import net.coljate.set.Set;

/**
 * A map whose values are computed. Also known as a lazy map or loading cache.
 *
 * @author Ollie
 * @see MutableCache
 * @see ConcurrentCache
 * @see ImmutableMap
 */
public interface Cache<K, V> extends Map<K, V> {

    /**
     * @return currently loaded keys.
     */
    @Override
    Set<K> keys();

    /**
     * @return currently loaded values.
     */
    @Override
    Collection<V> values();

    @Override
    default ImmutableMap<K, V> immutableCopy() {
        return Map.super.immutableCopy();
    }
    
}
