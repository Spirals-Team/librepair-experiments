package net.coljate.map.impl;

import java.util.Iterator;

import net.coljate.collection.Collection;
import net.coljate.list.List;
import net.coljate.map.AbstractMap;
import net.coljate.map.Entry;
import net.coljate.map.ImmutableEntry;
import net.coljate.set.Set;

/**
 *
 * @author Ollie
 */
public class RepeatedValueMap<K, V>
        extends AbstractMap<K, V> {

    public static <K, V> RepeatedValueMap<K, V> copyOf(final java.util.Collection<? extends K> keys, final V value) {
        final Set<K> keySet = Set.copyIntoHashSet(keys);
        return viewOf(keySet, value);
    }

    public static <K, V> RepeatedValueMap<K, V> viewOf(final Set<K> keys, final V value) {
        return new RepeatedValueMap<>(keys, value);
    }

    private final Set<K> keys;
    private final V value;

    protected RepeatedValueMap(final Set<K> keys, final V value) {
        this.keys = keys;
        this.value = value;
    }

    @Override
    public int count() {
        return keys.count();
    }

    @Override
    public boolean isEmpty() {
        return keys.isEmpty();
    }

    public V value() {
        return value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Entry<K, V> getEntry(final Object key) {
        return keys.contains(key)
                ? Entry.of((K) key, value)
                : null;
    }

    @Override
    public Set<K> keys() {
        return keys;
    }

    @Override
    public Collection<V> values() {
        return List.repeated(value, this.count());
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new RepeatedValueIterator();
    }

    private class RepeatedValueIterator implements Iterator<Entry<K, V>> {

        private final Iterator<? extends K> keyIterator = keys.iterator();

        @Override
        public boolean hasNext() {
            return keyIterator.hasNext();
        }

        @Override
        public Entry<K, V> next() {
            final K key = keyIterator.next();
            return ImmutableEntry.of(key, value);
        }

    }

}
