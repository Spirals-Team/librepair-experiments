package net.coljate.map.lazy;

import java.util.function.Function;

import net.coljate.collection.Collection;
import net.coljate.map.Entry;
import net.coljate.map.ImmutableMap;
import net.coljate.map.Map;
import net.coljate.map.MutableMap;
import net.coljate.set.Set;
import net.coljate.set.lazy.LazySet;

/**
 * A map whose key/value mappings are computed on the fly.
 *
 * These mappings may or may not be recomputed multiple times. To eagerly evaluate the map, copy it into a
 * {@link #mutableCopy() mutable} or {@link #immutableCopy() immutable} map.
 *
 * @author Ollie
 */
public interface LazyMap<K, V> extends LazySet<Entry<K, V>>, Map<K, V> {

    @Override
    default MutableMap<K, V> mutableCopy() {
        return Map.super.mutableCopy();
    }

    @Override
    default ImmutableMap<K, V> immutableCopy() {
        return Map.super.immutableCopy();
    }

    @Override
    Set<K> keys();

    @Override
    Collection<V> values();

    @Override
    default LazyMap<K, V> union(final K key, final V value) {
        return new LazyOverrideMap<>(this, key, value);
    }

    static <K, V> LazyMap<K, V> transformValues(
            final Set<? extends K> keys,
            final Function<? super K, ? extends V> valueFunction) {
        return new LazyTransformedKeyMap<>(keys, valueFunction);
    }

    static <K, V1, V2> LazyMap<K, V2> transformValues(
            final Collection<Entry<K, V1>> collection,
            final Function<? super V1, ? extends V2> transformation) {
        return transformValues(Map.copyOrCast(collection), transformation);
    }

    static <K, V1, V2> LazyMap<K, V2> transformValues(
            final Map<K, V1> map,
            final Function<? super V1, ? extends V2> transformation) {
        return new LazyTransformedValueMap<>(map, transformation);
    }

}
