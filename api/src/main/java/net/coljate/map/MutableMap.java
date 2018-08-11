package net.coljate.map;

import net.coljate.map.impl.MutableWrappedHashMap;
import net.coljate.map.impl.MutableWrappedMap;
import net.coljate.set.MutableSet;
import net.coljate.util.iterator.CovariantIterator;
import net.coljate.util.iterator.Iterators;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * @author Ollie
 * @see ImmutableMap
 * @since 1.0
 */
public interface MutableMap<K, V> extends Map<K, V>, MutableSet<Entry<K, V>> {

    @Override
    MutableEntry<K, V> getEntry(Object key);

    @CheckForNull
    V put(K key, V value);

    @CheckForNull
    default V put(final Entry<? extends K, ? extends V> entry) {
        return this.put(entry.key(), entry.value());
    }

    boolean remove(@Nullable Object key, @Nullable Object value);

    default boolean add(final K key, final V value) {
        return !this.containsKey(key)
                && this.putIfAbsent(key, value) == null;
    }

    default boolean addIfAbsent(final K key, final Function<? super K, ? extends V> valueFunction) {
        return !this.containsKey(key)
                && this.putIfAbsent(key, valueFunction.apply(key)) == null;
    }

    @Override
    default boolean add(final Entry<K, V> entry) {
        return this.add(entry.key(), entry.value());
    }

    default boolean addAll(@Nonnull final Map<? extends K, ? extends V> map) {
        boolean addedAny = false;
        for (final Entry<? extends K, ? extends V> entry : map) {
            addedAny &= this.add(entry.key(), entry.value());
        }
        return addedAny;
    }

    default Map<K, V> putAll(@Nonnull final Map<? extends K, ? extends V> map) {
        final MutableMap<K, V> put = MutableMap.createHashMap(map.count());
        for (final Entry<? extends K, ? extends V> entry : map) {
            put.put(entry.key(), this.put(entry.key(), entry.value()));
        }
        return put;
    }

    /**
     * Write a value to this map iff there is no mapping for the given key, or if the current value is null.
     *
     * @param key
     * @param value
     * @return the value previously associated to the given key, if any.
     */
    @CheckForNull
    default V putIfAbsent(final K key, final V value) {
        final V current = this.get(key);
        return current == null
                ? this.put(key, value)
                : current;
    }

    /**
     * Compute a new value, given the key and previous value.
     * <p>
     * Note that this method differs to {@link java.util.Map#compute} in that a null computed value will be stored
     * against the key, instead of causing the entry to be evicted.
     *
     * @param key
     * @param compute
     * @return
     */
    default V compute(final K key, final BiFunction<? super K, ? super V, ? extends V> compute) {
        final V previous = this.get(key);
        final V replacement = compute.apply(key, previous);
        this.put(key, replacement);
        return replacement;
    }

    default V computeIfAbsent(final K key, final Function<? super K, ? extends V> supplier) {
        final V current = this.get(key);
        if (current == null) {
            final V newValue = supplier.apply(key);
            if (newValue != null) {
                this.put(key, newValue);
                return newValue;
            }
        }
        return current;
    }

    default boolean replace(final K key, final V expectedValue, final V replacementValue) {
        final Entry<K, V> current = this.getEntry(key);
        if (current == null || !Objects.equals(current.value(), expectedValue)) {
            return false;
        }
        this.put(key, replacementValue);
        return true;
    }

    @Deprecated
    @Override
    default boolean remove(final Object entry) {
        return entry instanceof Entry
                && this.remove((Entry) entry);
    }

    default boolean remove(@Nullable final Entry<?, ?> entry) {
        return entry != null
                && this.remove(entry.key(), entry.value());
    }

    default boolean removeKey(final Object key) {
        return this.remove(this.getEntry(key));
    }

    @CheckForNull
    default V evict(@Nullable final Object key) {
        final Entry<K, V> entry = this.getEntry(key);
        return this.removeKey(key) ? entry.value() : null;
    }

    default boolean removeAllMatchingEntries(final BiPredicate<? super K, ? super V> predicate) {
        boolean removed = false;
        for (final Iterator<Entry<K, V>> iterator = this.iterator(); iterator.hasNext(); ) {
            final Entry<K, V> entry = iterator.next();
            if (predicate.test(entry.key(), entry.value())) {
                iterator.remove();
                removed = true;
            }
        }
        return removed;
    }

    @Override
    default Map<K, V> intersection(final Entry<K, V> entry) {
        this.put(entry);
        return this;
    }

    @Override
    default Map<K, V> union(final K key, final V value) {
        this.put(key, value);
        return this;
    }

    @Override
    default Spliterator<Entry<K, V>> spliterator() {
        return Map.super.spliterator();
    }

    @Override
    default CovariantIterator<Entry<K, V>, ? extends MutableEntry<K, V>> iterator() {
        return Iterators.transform(this.keys().iterator(), this::getEntry);
    }

    static <K, V> MutableMap<K, V> viewOf(final java.util.Map<K, V> map) {
        return MutableWrappedMap.viewOf(map);
    }

    static <K, V> MutableMap<K, V> createHashMap() {
        return MutableWrappedHashMap.create();
    }

    static <K, V> MutableMap<K, V> createHashMap(final int initialCapacity) {
        return MutableWrappedHashMap.create(initialCapacity);
    }

    static <K, V> MutableMap<K, V> copyIntoHashMap(final java.util.Map<K, V> map) {
        return MutableWrappedHashMap.copyOf(map);
    }

}
