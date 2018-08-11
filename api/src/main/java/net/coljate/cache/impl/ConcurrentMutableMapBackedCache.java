package net.coljate.cache.impl;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;

import net.coljate.collection.Collection;
import net.coljate.map.AbstractEntry;
import net.coljate.map.AbstractMap;
import net.coljate.map.ConcurrentMap;
import net.coljate.map.Entry;
import net.coljate.map.MutableEntry;
import net.coljate.set.Set;
import net.coljate.util.iterator.CovariantIterator;
import net.coljate.util.iterator.Iterators;
import net.coljate.cache.ConcurrentCache;
import net.coljate.util.functions.Functions;

/**
 *
 * @author Ollie
 */
public class ConcurrentMutableMapBackedCache<K, V>
        extends AbstractMap<K, V>
        implements ConcurrentCache<K, V> {

    public static <K, V> ConcurrentMutableMapBackedCache<K, V> create(final Function<? super K, ? extends V> valueFunction) {
        return new ConcurrentMutableMapBackedCache<>(ConcurrentMap.createHashMap(), valueFunction);
    }

    public static <K, V> ConcurrentMutableMapBackedCache<K, V> copyOf(
            final Iterable<? extends Entry<K, V>> map,
            final Function<? super K, ? extends V> valueFunction) {
        final ConcurrentMutableMapBackedCache<K, V> cache = create(valueFunction);
        cache.addAll(map);
        return cache;
    }

    private final ConcurrentMap<K, Computer<K, V>> map;
    private final Function<? super K, ? extends V> valueFunction;

    private ConcurrentMutableMapBackedCache(
            final ConcurrentMap<K, Computer<K, V>> map,
            final Function<? super K, ? extends V> valueFunction) {
        this.map = map;
        this.valueFunction = valueFunction;
    }

    @Override
    public V get(final K key) {
        return map.computeIfAbsent(key, k -> new Computing()).compute(key);
    }

    @Override
    public V getIfPresent(final Object key) {
        final Computer<K, V> holder = map.getIfPresent(key);
        return holder == null ? null : holder.current();
    }

    @Override
    public V put(final K key, final V value) {
        return Functions.ifNonNull(map.put(key, new Computed<>(value)), Computer::current);
    }

    @Override
    public boolean add(final K key, final V value) {
        return map.add(key, new Computed<>(value));
    }

    @Override
    public boolean remove(final Object key, final Object value) {
        return map.remove(key, new Computed<>(value));
    }

    @Override
    public boolean removeKey(final Object key) {
        return map.removeKey(key);
    }

    @Override
    public V evict(final Object key) {
        return key == null
                ? null
                : Functions.ifNonNull(map.evict(key), Computer::current);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean containsEntry(final Entry<?, ?> entry) {
        final Entry<K, Computer<K, V>> current = map.getEntry(entry.key());
        final Computer<K, V> computer = current == null ? null : current.value();
        return current != null
                && computer != null
                && !computer.isComputing()
                && Objects.equals(current.key(), entry.key())
                && Objects.equals(computer.current(), entry.value());
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public Set<K> keys() {
        return map.keys();
    }

    @Override
    public Collection<V> values() {
        return map.values().transform(Computer::current);
    }

    @Override
    public V putIfAbsent(final K key, final V value) {
        return Functions.ifNonNull(map.putIfAbsent(key, new Computed<>(value)), Computer::current);
    }

    @Override
    public V computeIfAbsent(final K key, final Function<? super K, ? extends V> supplier) {
        return Functions.ifNonNull(map.computeIfAbsent(key, k -> new Computed<>(supplier.apply(k))), Computer::current);
    }

    @Override
    public boolean replace(final K key, final V expectedValue, final V replacementValue) {
        return key != null
                && map.replace(key, new Computed<>(expectedValue), new Computed<>(replacementValue));
    }

    @Override
    public MutableEntry<K, V> getEntry(final Object key) {
        return key == null
                ? null
                : this.translate(map.getEntry(key));
    }

    @Override
    public CovariantIterator<Entry<K, V>, MutableEntry<K, V>> iterator() {
        final Iterator<MutableEntry<K, V>> translated = Iterators.transform(map.iterator(), this::translate);
        final Iterator<MutableEntry<K, V>> filtered = Iterators.filter(translated, Objects::nonNull);
        return CovariantIterator.of(filtered);
    }

    private MutableEntry<K, V> translate(final Entry<K, Computer<K, V>> entry) {
        return entry == null
                ? null
                : new EntryWriter(entry.key());
    }

    @Override
    public ConcurrentMutableMapBackedCache<K, V> mutableCopy() {
        final ConcurrentMutableMapBackedCache<K, V> copy = create(valueFunction);
        copy.putAll(this);
        return copy;
    }

    private interface Computer<K, V> {

        V current();

        V compute(K key);

        boolean isComputing();

    }

    private final class Computing implements Computer<K, V> {

        final Object lock = new Object();
        int timesAccessed = 0;

        @Override
        public V current() {
            return null;
        }

        @Override
        public V compute(final K key) {
            synchronized (lock) {
                if (timesAccessed++ > 1) {
                    return ConcurrentMutableMapBackedCache.this.get(key);
                }
                final V value = valueFunction.apply(key);
                map.put(key, new Computed<>(value));
                return value;
            }
        }

        @Override
        public boolean isComputing() {
            return true;
        }

    }

    private static final class Computed<K, V> implements Computer<K, V> {

        private final V value;

        Computed(final V value) {
            this.value = value;
        }

        @Override
        public V current() {
            return value;
        }

        @Override
        public V compute(final K key) {
            return value;
        }

        @Override
        public boolean isComputing() {
            return false;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 89 * hash + Objects.hashCode(this.value);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Computed
                    && Objects.equals(value, ((Computed) obj).value);
        }

    }

    private final class EntryWriter
            extends AbstractEntry<K, V>
            implements MutableEntry<K, V> {

        private final K key;

        EntryWriter(final K key) {
            this.key = key;
        }

        @Override
        public K key() {
            return key;
        }

        @Override
        public V value() {
            return get(key);
        }

        @Override
        public void setValue(final V value) {
            put(key, value);
        }

    }

}
