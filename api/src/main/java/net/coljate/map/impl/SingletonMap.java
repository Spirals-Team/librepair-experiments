package net.coljate.map.impl;

import net.coljate.collection.ImmutableCollection;
import net.coljate.collection.Singleton;
import net.coljate.map.AbstractMap;
import net.coljate.map.Entry;
import net.coljate.map.ImmutableEntry;
import net.coljate.map.ImmutableMap;
import net.coljate.set.ImmutableSet;
import net.coljate.util.iterator.UnmodifiableIterator;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Ollie
 * @see EmptyMap
 */
public class SingletonMap<K, V>
        extends AbstractMap<K, V>
        implements Singleton<Entry<K, V>>, ImmutableMap<K, V>, ImmutableEntry<K, V>, Serializable {

    private static final long serialVersionUID = 1L;

    public static <K, V> SingletonMap<K, V> of(final K key, final V value) {
        return new SingletonMap<>(key, value);
    }

    private final K key;
    private final V value;
    private transient ImmutableSet<K> keys;
    private transient ImmutableCollection<V> values;

    protected SingletonMap(final K key, final V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K key() {
        return key;
    }

    @Override
    public V value() {
        return value;
    }

    @Override
    public ImmutableEntry<K, V> element() {
        return this;
    }

    @Override
    public boolean contains(final Object object) {
        return ImmutableMap.super.contains(object);
    }

    @Override
    public boolean contains(Object key, Object value) {
        return ImmutableEntry.super.contains(key, value);
    }

    @Override
    public ImmutableSet<K> keys() {
        return keys == null
                ? (keys = ImmutableSet.of(this.key()))
                : keys;
    }

    @Override
    public ImmutableCollection<V> values() {
        return values == null
                ? (values = ImmutableCollection.of(this.value()))
                : values;
    }

    @Override
    public SingletonMap<K, V> getEntry(final Object key) {
        return Objects.equals(this.key(), key)
                ? this
                : null;
    }

    @Override
    public ImmutableMap<K, V> with(final K key, final V value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public UnmodifiableIterator<Entry<K, V>> iterator() {
        return UnmodifiableIterator.of(this);
    }

    @Deprecated
    @Override
    public SingletonMap<K, V> immutableCopy() {
        return this;
    }

    @Override
    public boolean equals(final Object object) {
        return super.equals(object)
                || (!(object instanceof SingletonMap) && object instanceof Entry && object.equals(this));
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ":[" + key + "=" + value + "]";
    }

}
