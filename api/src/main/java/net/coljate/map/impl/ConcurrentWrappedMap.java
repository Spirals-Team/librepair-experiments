package net.coljate.map.impl;

import net.coljate.map.ConcurrentMap;

/**
 *
 * @author ollie
 */
public class ConcurrentWrappedMap<K, V>
        extends MutableWrappedMap<K, V>
        implements ConcurrentMap<K, V> {

    public ConcurrentWrappedMap(final java.util.concurrent.ConcurrentMap<K, V> delegate) {
        super(delegate);
    }

    @Override
    public java.util.concurrent.ConcurrentHashMap<K, V> mutableJavaMapCopy() {
        return this.javaMapCopy(java.util.concurrent.ConcurrentHashMap::new);
    }

    @Override
    public ConcurrentMap<K, V> mutableCopy() {
        return ConcurrentMap.super.mutableCopy();
    }

}
