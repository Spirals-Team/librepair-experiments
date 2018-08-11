package net.coljate.map.impl;

import net.coljate.collection.Empty;
import net.coljate.collection.ImmutableCollection;
import net.coljate.map.AbstractMap;
import net.coljate.map.Entry;
import net.coljate.map.ImmutableEntry;
import net.coljate.map.ImmutableMap;
import net.coljate.set.ImmutableSet;
import net.coljate.util.iterator.UnmodifiableIterator;

import javax.annotation.CheckReturnValue;
import java.io.Serializable;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Ollie
 * @see SingletonMap
 */
public class EmptyMap<K, V>
        extends AbstractMap<K, V>
        implements Empty<Entry<K, V>>, ImmutableMap<K, V>, Serializable {

    private static final long serialVersionUID = 1L;
    private static final EmptyMap INSTANCE = new EmptyMap();

    public static <K, V> EmptyMap<K, V> instance() {
        return INSTANCE;
    }

    protected EmptyMap() {
    }

    @Override
    @Deprecated
    public boolean contains(final Object object) {
        return Empty.super.contains(object);
    }

    @Override
    @Deprecated
    public ImmutableEntry<K, V> getEntry(final Object key) {
        return null;
    }

    @Override
    @Deprecated
    public ImmutableSet<K> keys() {
        return ImmutableSet.of();
    }

    @Override
    @Deprecated
    public ImmutableCollection<V> values() {
        return ImmutableCollection.of();
    }

    @Override
    @Deprecated
    public <V2> EmptyMap<K, V2> transformValues(final Function<? super V, ? extends V2> transform) {
        return instance();
    }

    @Override
    @Deprecated
    public EmptyMap<K, V> filter(final Predicate<? super Entry<K, V>> predicate) {
        return this;
    }

    @Override
    @CheckReturnValue
    public SingletonMap<K, V> with(final K key, final V value) {
        return SingletonMap.of(key, value);
    }

    @Override
    @Deprecated
    public UnmodifiableIterator<Entry<K, V>> iterator() {
        return Empty.super.iterator();
    }

    @Override
    @Deprecated
    public Spliterator<Entry<K, V>> spliterator() {
        return Empty.super.spliterator();
    }

    @Override
    @Deprecated
    public EmptyMap<K, V> immutableCopy() {
        return this;
    }

}
