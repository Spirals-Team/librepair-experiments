package net.coljate.map.impl;

import net.coljate.collection.Collection;
import net.coljate.map.Entry;

/**
 *
 * @author Ollie
 */
public class ConcurrentWrappedHashMap<K, V>
        extends ConcurrentWrappedMap<K, V>
        implements HashMap<K, V> {

    public static final int DEFAULT_INITIAL_CAPACITY = 10;

    public static <K, V> ConcurrentWrappedHashMap<K, V> create() {
        return create(DEFAULT_INITIAL_CAPACITY);
    }

    public static <K, V> ConcurrentWrappedHashMap<K, V> create(final int initialCapacity) {
        return new ConcurrentWrappedHashMap<>(new java.util.concurrent.ConcurrentHashMap<>(initialCapacity));
    }

    public static <K, V> ConcurrentWrappedHashMap<K, V> copyOf(final java.util.Collection<? extends Entry<? extends K, ? extends V>> entries) {
        final ConcurrentWrappedHashMap<K, V> map = create(entries.size());
        entries.forEach(entry -> map.put(entry.key(), entry.value()));
        return map;
    }

    public static <K, V> ConcurrentWrappedHashMap<K, V> copyOf(final Collection<? extends Entry<? extends K, ? extends V>> entries) {
        final ConcurrentWrappedHashMap<K, V> map = create(entries.count());
        entries.forEach(entry -> map.put(entry.key(), entry.value()));
        return map;
    }

    protected ConcurrentWrappedHashMap(final java.util.concurrent.ConcurrentHashMap<K, V> delegate) {
        super(delegate);
    }

    @Override
    public ConcurrentWrappedHashMap<K, V> mutableCopy() {
        return new ConcurrentWrappedHashMap<>(this.mutableJavaMapCopy());
    }

}
