package net.coljate.map.impl;

import javax.annotation.Nonnull;

import net.coljate.collection.Collection;
import net.coljate.map.AbstractMap;
import net.coljate.map.Entry;
import net.coljate.map.ImmutableMap;
import net.coljate.map.Map;
import net.coljate.map.MutableMap;
import net.coljate.set.Set;

/**
 *
 * @author Ollie
 */
public abstract class DelegatedMap<K, V>
        extends AbstractMap<K, V> {

    @Nonnull
    protected abstract Map<K, V> delegate();

    @Override
    public boolean containsKey(final Object key) {
        return this.delegate().containsKey(key);
    }

    @Override
    public Entry<K, V> getEntry(final Object key) {
        return this.delegate().getEntry(key);
    }

    @Override
    public V getIfPresent(final Object key) {
        return this.delegate().getIfPresent(key);
    }

    @Override
    public Set<K> keys() {
        return this.delegate().keys();
    }

    @Override
    public int count() {
        return this.delegate().count();
    }

    @Override
    public boolean isEmpty() {
        return this.delegate().isEmpty();
    }

    @Override
    public Collection<V> values() {
        return this.delegate().values();
    }

    @Override
    public java.util.Map<K, V> mutableJavaMapCopy() {
        return this.delegate().mutableJavaMapCopy();
    }

    @Override
    public MutableMap<K, V> mutableCopy() {
        return this.delegate().mutableCopy();
    }

    @Override
    public ImmutableMap<K, V> immutableCopy() {
        return this.delegate().immutableCopy();
    }

}
