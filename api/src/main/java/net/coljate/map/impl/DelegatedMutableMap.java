package net.coljate.map.impl;

import net.coljate.collection.Collection;
import net.coljate.map.MutableEntry;
import net.coljate.map.MutableMap;
import net.coljate.set.Set;

/**
 *
 * @author Ollie
 */
public abstract class DelegatedMutableMap<K, V>
        extends DelegatedMap<K, V>
        implements MutableMap<K, V> {

    @Override
    protected abstract MutableMap<K, V> delegate();

    @Override
    public MutableEntry<K, V> getEntry(Object key) {
        return this.delegate().getEntry(key);
    }

    @Override
    public V put(final K key, final V value) {
        return this.delegate().put(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return this.delegate().remove(key, value);
    }

    @Override
    public void clear() {
        this.delegate().clear();
    }

}
