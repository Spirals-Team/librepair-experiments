package net.coljate.map.impl;

import java.util.function.Function;

import javax.annotation.Nonnull;

import net.coljate.map.AbstractEntry;
import net.coljate.map.Entry;
import net.coljate.map.MutableEntry;
import net.coljate.map.MutableMap;
import net.coljate.util.iterator.CovariantIterator;

/**
 *
 * @author Ollie
 */
@SuppressWarnings("element-type-mismatch")
public class MutableWrappedMap<K, V>
        extends WrappedMap<K, V>
        implements MutableMap<K, V> {

    public static <K, V> MutableWrappedMap<K, V> viewOf(final java.util.Map<K, V> map) {
        return map instanceof java.util.HashMap
                ? MutableWrappedHashMap.viewOf((java.util.HashMap<K, V>) map)
                : new MutableWrappedMap<>(map);
    }

    protected static <K, V> java.util.HashMap<K, V> copyIntoHashMap(final java.util.Map<K, V> map) {
        return new java.util.HashMap<>(map);
    }

    private final java.util.Map<K, V> delegate;

    protected MutableWrappedMap(@Nonnull final java.util.Map<K, V> delegate) {
        super(delegate);
        this.delegate = delegate;
    }

    @Override
    public V put(final K key, final V value) {
        return delegate.put(key, value);
    }

    @Override
    public boolean remove(final Object key, final Object value) {
        return delegate.remove(key, value);
    }

    @Override
    @SuppressWarnings("element-type-mismatch")
    public boolean removeKey(final Object key) {
        return delegate.keySet().remove(key);
    }

    @Override
    @SuppressWarnings("element-type-mismatch")
    public V evict(final Object key) {
        return delegate.remove(key);
    }

    @Override
    public V putIfAbsent(final K key, final V value) {
        return delegate.putIfAbsent(key, value);
    }

    @Override
    public V computeIfAbsent(final K key, final Function<? super K, ? extends V> supplier) {
        return delegate.computeIfAbsent(key, supplier);
    }

    @Override
    public boolean replace(final K key, final V expectedValue, final V replacementValue) {
        return delegate.replace(key, expectedValue, replacementValue);
    }

    @Override
    @SuppressWarnings("unchecked")
    public MutableEntry<K, V> getEntry(final Object key) {
        return delegate.containsKey(key)
                ? new MutableWrappedEntry((K) key)
                : null;
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public CovariantIterator<Entry<K, V>, ? extends MutableEntry<K, V>> iterator() {
        return MutableMap.super.iterator();
    }

    protected class MutableWrappedEntry
            extends AbstractEntry<K, V>
            implements MutableEntry<K, V> {

        private final K key;

        MutableWrappedEntry(final K key) {
            this.key = key;
        }

        @Override
        public K key() {
            return key;
        }

        @Override
        public V value() {
            return delegate.get(key);
        }

        @Override
        public void setValue(final V value) {
            delegate.put(key, value);
        }

        @Override
        public V getAndSetValue(final V value) {
            return delegate.replace(key, value);
        }

    }

}
