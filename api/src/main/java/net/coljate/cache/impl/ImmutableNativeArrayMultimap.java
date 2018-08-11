package net.coljate.cache.impl;

import net.coljate.cache.ImmutableListMultimap;
import net.coljate.cache.MutableListMultimap;
import net.coljate.collection.Collection;
import net.coljate.collection.ImmutableCollection;
import net.coljate.list.ImmutableList;
import net.coljate.list.impl.ImmutableNativeArray;
import net.coljate.map.Entry;
import net.coljate.map.ImmutableMap;
import net.coljate.set.ImmutableSet;
import net.coljate.util.iterator.UnmodifiableCovariantIterator;

/**
 *
 * @author Ollie
 */
public class ImmutableNativeArrayMultimap<K, V>
        extends AbstractMapBackedMultimap<K, V, ImmutableNativeArray<V>>
        implements ImmutableListMultimap<K, V> {

    protected ImmutableNativeArrayMultimap(final ImmutableMap<K, ImmutableNativeArray<V>> map) {
        super(map);
    }

    @Override
    public MutableListMultimap<K, V> mutableCopy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ImmutableMultimapEntry<K, V, ? extends ImmutableList<V>> getEntry(Object key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ImmutableSet<K> keys() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ImmutableCollection<Collection<V>> values() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ImmutableMap<K, Collection<V>> with(K key, Collection<V> value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public UnmodifiableCovariantIterator<Entry<K, Collection<V>>, ?> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
