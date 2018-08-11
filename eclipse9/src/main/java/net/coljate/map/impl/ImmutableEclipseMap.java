package net.coljate.map.impl;

import net.coljate.collection.ImmutableCollection;
import net.coljate.collection.impl.EclipseRichIterableCollection;
import net.coljate.map.AbstractMap;
import net.coljate.map.ImmutableEntry;
import net.coljate.map.ImmutableMap;
import net.coljate.set.ImmutableSet;
import net.coljate.set.MutableSet;
import net.coljate.util.iterator.UnmodifiableIterator;

import org.eclipse.collections.api.RichIterable;

/**
 *
 * @author Ollie
 */
public class ImmutableEclipseMap<K, V> extends AbstractMap<K, V> implements ImmutableMap<K, V> {

    private final org.eclipse.collections.api.map.ImmutableMap<K, V> map;
    private ImmutableSet<K> keys;
    private ImmutableCollection<V> values;

    public ImmutableEclipseMap(final org.eclipse.collections.api.map.ImmutableMap<K, V> map) {
        this.map = map;
    }

    @Override
    public boolean containsKey(final Object key) {
        return map.containsKey(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ImmutableEntry<K, V> getEntry(final Object key) {
        final V value = map.get(key);
        return value == null && !this.containsKey(key)
                ? null
                : ImmutableEntry.of((K) key, value);
    }

    @Override
    public ImmutableSet<K> keys() {
        return keys == null
                ? (keys = new KeySet<>(map.keysView()))
                : keys;
    }

    @Override
    public ImmutableCollection<V> values() {
        return values == null
                ? (values = new Values<>(map.valuesView()))
                : values;
    }

    @Override
    public ImmutableEclipseMap<K, V> with(final K key, V value) {
        final org.eclipse.collections.api.map.ImmutableMap<K, V> newMap = map.newWithKeyValue(key, value);
        return newMap == map
                ? this
                : new ImmutableEclipseMap<>(newMap);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    private static final class KeySet<K> extends EclipseRichIterableCollection<K> implements ImmutableSet<K> {

        KeySet(final RichIterable<? extends K> iterable) {
            super(iterable);
        }

        @Override
        public UnmodifiableIterator<K> iterator() {
            throw new UnsupportedOperationException(); //TODO
        }

        @Override
        public KeySet<K> immutableCopy() {
            return this;
        }

        @Override
        public MutableSet<K> mutableCopy() {
            return ImmutableSet.super.mutableCopy();
        }

    }

    private static final class Values<V> extends EclipseRichIterableCollection<V> implements ImmutableCollection<V> {

        Values(final RichIterable<? extends V> iterable) {
            super(iterable);
        }

        @Override
        public UnmodifiableIterator<V> iterator() {
            return UnmodifiableIterator.wrap(super.iterator());
        }

    }

}
