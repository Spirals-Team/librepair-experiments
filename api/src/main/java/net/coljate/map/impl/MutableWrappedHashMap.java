package net.coljate.map.impl;

import java.io.Serializable;

import net.coljate.map.Entry;

/**
 *
 * @author Ollie
 */
public class MutableWrappedHashMap<K, V>
        extends MutableWrappedMap<K, V>
        implements HashMap<K, V>, Serializable {

    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_INITIAL_CAPACITY = 10;

    public static <K, V> MutableWrappedHashMap<K, V> copyOf(final java.util.Collection<? extends Entry<? extends K, ? extends V>> entries) {
        final java.util.HashMap<K, V> map = new java.util.HashMap<>(entries.size());
        entries.forEach(entry -> map.put(entry.key(), entry.value()));
        return viewOf(map);
    }

    public static <K, V> MutableWrappedHashMap<K, V> create() {
        return create(DEFAULT_INITIAL_CAPACITY);
    }

    public static <K, V> MutableWrappedHashMap<K, V> create(final int initialCapacity) {
        return viewOf(new java.util.HashMap<>(initialCapacity));
    }

    public static <K, V> MutableWrappedHashMap<K, V> copyOf(final java.util.Map<K, V> map) {
        return viewOf(MutableWrappedMap.copyIntoHashMap(map));
    }

    public static <K, V> MutableWrappedHashMap<K, V> viewOf(final java.util.HashMap<K, V> map) {
        return new MutableWrappedHashMap<>(map);
    }

    private final java.util.HashMap<K, V> delegate;

    public MutableWrappedHashMap(final java.util.HashMap<K, V> delegate) {
        super(delegate);
        this.delegate = delegate;
    }

    @Override
    public java.util.HashMap<K, V> mutableJavaMapCopy() {
        return new java.util.HashMap<>(delegate);
    }

    @Override
    public MutableWrappedHashMap<K, V> mutableCopy() {
        return new MutableWrappedHashMap<>(this.mutableJavaMapCopy());
    }

}
