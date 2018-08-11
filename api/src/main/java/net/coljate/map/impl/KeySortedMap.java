package net.coljate.map.impl;

import java.util.Comparator;

import net.coljate.collection.Collection;
import net.coljate.map.AbstractMap;
import net.coljate.map.Entry;
import net.coljate.map.ImmutableMap;
import net.coljate.map.Map;
import net.coljate.map.MutableSortedMap;
import net.coljate.map.SortedMap;
import net.coljate.set.SortedSet;

/**
 *
 * @author Ollie
 */
public class KeySortedMap<K, V>
        extends AbstractMap<K, V>
        implements SortedMap<K, V> {

    public static <K, V> KeySortedMap<K, V> copyOf(final Map<K, V> map, final Comparator<? super K> comparator) {
        final ImmutableMap<K, V> immutableMap = map.immutableCopy();
        final SortedSet<K> sortedKeys = immutableMap.keys().sortedCopy(comparator);
        return new KeySortedMap<>(immutableMap, sortedKeys);
    }

    private final ImmutableMap<K, V> map;
    private final SortedSet<K> keys;

    protected KeySortedMap(final ImmutableMap<K, V> map, final SortedSet<K> keys) {
        this.map = map;
        this.keys = keys;
    }

    @Override
    public SortedSet<K> keys() {
        return keys;
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Comparator<? super Entry<K, V>> comparator() {
        return (l, r) -> keys.comparator().compare(l.key(), r.key());
    }

    @Override
    public Entry<K, V> getEntry(final Object key) {
        return map.getEntry(key);
    }

    @Override
    public Entry<K, V> least() {
        return this.getEntry(keys.least());
    }

    @Override
    public Entry<K, V> greatest() {
        return this.getEntry(keys.greatest());
    }

    @Override
    public MutableSortedMap<K, V> mutableCopy() {
        throw new UnsupportedOperationException(); //TODO
    }

}
