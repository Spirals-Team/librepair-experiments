package net.coljate.map.lazy;

import java.util.Objects;

import net.coljate.collection.Collection;
import net.coljate.collection.lazy.LazyAppendCollection;
import net.coljate.map.Entry;
import net.coljate.map.Map;
import net.coljate.map.impl.SimpleImmutableEntry;
import net.coljate.set.Set;

/**
 *
 * @author Ollie
 */
public class LazyOverrideMap<K, V> implements LazyMap<K, V> {

    private final Map<K, V> map;
    private final K key;
    private final V value;

    public LazyOverrideMap(final Map<K, V> map, final K key, final V value) {
        this.map = map;
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean containsKey(final Object key) {
        return Objects.equals(this.key, key) || map.containsKey(key);
    }

    @Override
    public Set<K> keys() {
        return map.keys().union(key);
    }

    @Override
    public Collection<V> values() {
        return LazyAppendCollection.of(map.values(), value);
    }

    @Override
    public V get(final K key) {
        return Objects.equals(this.key, key)
                ? value
                : map.get(key);
    }

    @Override
    public Entry<K, V> getEntry(final Object key) {
        return Objects.equals(this.key, key)
                ? new SimpleImmutableEntry<>(this.key, value)
                : map.getEntry(key);
    }

}
