package net.coljate.cache.impl;

import java.util.function.Function;

import net.coljate.cache.Multimap;
import net.coljate.collection.Collection;
import net.coljate.map.AbstractEntry;
import net.coljate.map.AbstractMap;
import net.coljate.map.Entry;
import net.coljate.map.ImmutableMap;
import net.coljate.map.Map;
import net.coljate.map.MutableMap;
import net.coljate.set.Set;
import net.coljate.util.functions.Functions;

/**
 *
 * @author Ollie
 */
public abstract class AbstractMapBackedMultimap<K, V, C extends Collection<V>>
        extends AbstractMap<K, Collection<V>>
        implements Multimap<K, V> {

    private final Map<K, C> cache;

    protected AbstractMapBackedMultimap(final Map<K, C> cache) {
        this.cache = cache;
    }

    protected MutableMap<K, C> mutableCacheCopy(final Function<C, C> mutableCopy) {
        final MutableMap<K, C> copy = cache.mutableCopy();
        this.cache.forEach((key, collection) -> copy.put(key, mutableCopy.apply(collection)));
        return copy;
    }

    protected <R> ImmutableMap<K, R> immutableCacheCopy(final Function<C, R> immutableCopy) {
        return cache.mutableCopy()
                .transformValues(immutableCopy)
                .immutableCopy();
    }

    @Override
    public Set<K> keys() {
        return cache.keys();
    }

    @Override
    public Collection<Collection<V>> values() {
        return Collection.viewOf(cache.values());
    }

    @Override
    public C get(final K key) {
        return cache.get(key);
    }

    @Override
    public MultimapEntry<K, V, ?> getEntry(final Object key) {
        return Functions.ifNonNull(cache.getEntry(key), WrappedEntry::new);
    }

    protected class WrappedEntry<E extends Entry<K, C>>
            extends AbstractEntry<K, Collection<V>>
            implements MultimapEntry<K, V, C> {

        protected final E entry;

        WrappedEntry(final E entry) {
            this.entry = entry;
        }

        @Override
        public K key() {
            return entry.key();
        }

        @Override
        public C value() {
            return entry.value();
        }

    }

}
