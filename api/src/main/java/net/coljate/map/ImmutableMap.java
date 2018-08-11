package net.coljate.map;

import net.coljate.collection.ImmutableCollection;
import net.coljate.map.impl.EmptyMap;
import net.coljate.map.impl.ImmutableWrappedMap;
import net.coljate.map.impl.SingletonMap;
import net.coljate.set.ImmutableSet;
import net.coljate.util.iterator.Iterators;
import net.coljate.util.iterator.UnmodifiableIterator;

import javax.annotation.CheckForNull;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Spliterator;
import java.util.Spliterators;

/**
 * @author Ollie
 * @see MutableMap
 * @see EmptyMap
 * @see SingletonMap
 * @since 1.0
 */
public interface ImmutableMap<K, V> extends Map<K, V>, ImmutableSet<Entry<K, V>> {

    @Override
    @CheckForNull
    ImmutableEntry<K, V> getEntry(@Nullable Object key);

    @Override
    ImmutableSet<K> keys();

    @Override
    ImmutableCollection<V> values();

    @Override
    default ImmutableMap<K, V> with(@Nonnull final Entry<K, V> entry) {
        return this.with(entry.key(), entry.value());
    }

    @Nonnull
    @CheckReturnValue
    ImmutableMap<K, V> with(K key, V value);

    @Override
    @Deprecated
    default ImmutableMap<K, V> immutableCopy() {
        return this;
    }

    @Override
    default UnmodifiableIterator<Entry<K, V>> iterator() {
        return Iterators.transform(this.keys().iterator(), this::getEntry);
    }

    @Override
    default Spliterator<Entry<K, V>> spliterator() {
        return Spliterators.spliterator(this.iterator(), this.count(), Spliterator.SIZED | Spliterator.DISTINCT | Spliterator.NONNULL | Spliterator.IMMUTABLE);
    }

    static <K, V> EmptyMap<K, V> of() {
        return EmptyMap.instance();
    }

    static <K, V> SingletonMap<K, V> of(final K key, final V value) {
        return SingletonMap.of(key, value);
    }

    static <K, V> ImmutableMap<K, V> of(final K k1, final V v1, final K k2, final V v2) {
        final MutableMap<K, V> map = MutableMap.createHashMap(2);
        map.put(k1, v1);
        map.put(k2, v2);
        return map.immutableCopy();
    }

    static <K, V> ImmutableMap<K, V> copyIntoHashMap(final Map<? extends K, ? extends V> map) {
        return ImmutableWrappedMap.copyIntoHashMap(map);
    }

}
