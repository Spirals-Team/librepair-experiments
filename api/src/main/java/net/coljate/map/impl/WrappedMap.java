package net.coljate.map.impl;

import java.util.Iterator;
import java.util.Objects;

import javax.annotation.Nonnull;

import net.coljate.collection.Collection;
import net.coljate.map.AbstractMap;
import net.coljate.map.Entry;
import net.coljate.map.ImmutableEntry;
import net.coljate.map.ImmutableMap;
import net.coljate.map.Map;
import net.coljate.map.MutableMap;
import net.coljate.set.Set;
import net.coljate.util.iterator.Iterators;

/**
 *
 * @author Ollie
 */
@SuppressWarnings("element-type-mismatch")
public class WrappedMap<K, V>
        extends AbstractMap<K, V>
        implements Map<K, V> {

    @Nonnull
    private final java.util.Map<K, V> map;
    private Set<K> keys;
    private Collection<V> values;

    protected WrappedMap(final java.util.Map<K, V> delegate) {
        this.map = Objects.requireNonNull(delegate);
    }

    @Override
    public java.util.Map<K, V> mutableJavaMapCopy() {
        return new java.util.HashMap<>(map);
    }

    @Override
    public boolean containsKey(final Object key) {
        return map.containsKey(key);
    }

    @Override
    public V get(final K key) {
        return map.get(key);
    }

    @Override
    @SuppressWarnings("element-type-mismatch")
    public V getIfPresent(final Object key) {
        return map.get(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Entry<K, V> getEntry(final Object key) {
        return ViewEntry.viewOf(key, this);
    }

    @Override
    public Set<K> keys() {
        return keys == null
                ? (keys = Set.viewOf(map.keySet()))
                : keys;
    }

    @Override
    public Collection<V> values() {
        return values == null
                ? (values = Collection.viewOf(map.values()))
                : values;
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return Iterators.transform(
                map.entrySet().iterator(),
                entry -> ImmutableEntry.of(entry.getKey(), entry.getValue()));
    }

    @Override
    public MutableMap<K, V> mutableCopy() {
        return new MutableWrappedMap<>(this.mutableJavaMapCopy());
    }

    @Override
    public ImmutableMap<K, V> immutableCopy() {
        return new ImmutableWrappedMap<>(this.mutableJavaMapCopy());
    }

}
