package net.coljate.cache.impl;

import net.coljate.cache.ImmutableMultimap;
import net.coljate.cache.Multimap;
import net.coljate.cache.MutableMultimap;
import net.coljate.collection.Collection;
import net.coljate.map.AbstractEntry;
import net.coljate.map.AbstractMap;
import net.coljate.map.Entry;
import net.coljate.set.Set;
import net.coljate.util.iterator.CovariantIterator;

/**
 *
 * @author Ollie
 */
public class GuavaMultimap<K, V>
        extends AbstractMap<K, Collection<V>>
        implements Multimap<K, V> {

    private final com.google.common.collect.Multimap<K, V> multimap;
    private Set<K> keys;

    protected GuavaMultimap(final com.google.common.collect.Multimap<K, V> multimap) {
        this.multimap = multimap;
    }

    @Override
    public Collection<V> get(final K key) {
        return Collection.viewOf(multimap.get(key));
    }

    @Override
    @SuppressWarnings("unchecked")
    public MultimapEntry<K, V, ?> getEntry(final Object key) {
        return multimap.containsKey(key)
                ? new GuavaMultimapEntry((K) key)
                : null;
    }

    @Override
    public CovariantIterator<Entry<K, Collection<V>>, ?> iterator() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public Set<K> keys() {
        return keys == null
                ? keys = Set.viewOf(multimap.keySet())
                : keys;
    }

    @Override
    public Collection<Collection<V>> values() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public MutableMultimap<K, V> mutableCopy() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public ImmutableMultimap<K, V> immutableCopy() {
        throw new UnsupportedOperationException(); //TODO
    }

    private class GuavaMultimapEntry
            extends AbstractEntry<K, Collection<V>>
            implements MultimapEntry<K, V, Collection<V>> {

        private final K key;

        protected GuavaMultimapEntry(final K key) {
            this.key = key;
        }

        @Override
        public K key() {
            return key;
        }

        @Override
        public Collection<V> value() {
            return Collection.viewOf(multimap.get(key));
        }

    }

}
