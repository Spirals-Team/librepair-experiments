package net.coljate.map.primitive;

import java.util.Objects;
import java.util.OptionalDouble;

import net.coljate.collection.primitive.DoubleCollection;
import net.coljate.collection.primitive.TroveDoubleCollection;
import net.coljate.map.AbstractMap;
import net.coljate.set.Set;

import gnu.trove.map.TObjectDoubleMap;
import gnu.trove.map.hash.TObjectDoubleHashMap;

/**
 *
 * @author Ollie
 */
public class TroveDoubleValueMap<K> extends AbstractMap<K, Double> implements DoubleValueMap<K> {

    public static <K> DoubleValueMap<K> createHashMap(final int initialCapacity) {
        return new TroveDoubleValueMap<>(new TObjectDoubleHashMap<>(initialCapacity));
    }

    public static <K> DoubleValueMap<K> copyIntoHashMap(final TObjectDoubleMap<? extends K> map) {
        return new TroveDoubleValueMap<>(new TObjectDoubleHashMap<>(map));
    }

    private final TObjectDoubleMap<K> map;
    private Set<K> keys;
    private DoubleCollection values;

    protected TroveDoubleValueMap(final TObjectDoubleMap<K> map) {
        this.map = Objects.requireNonNull(map);
    }

    @Override
    public double defaultValue() {
        return map.getNoEntryValue();
    }

    @Override
    public double getDouble(final K key) {
        return map.get(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public DoubleValueEntry<K> getEntry(final Object key) {
        return map.containsKey(key)
                ? new Entry((K) key)
                : null;
    }

    @Override
    public OptionalDouble maybeGetDouble(final Object key) {
        return map.containsKey(key)
                ? OptionalDouble.of(map.get(key))
                : OptionalDouble.empty();
    }

    @Override
    public Set<K> keys() {
        return keys == null
                ? (keys = Set.viewOf(map.keySet()))
                : keys;
    }

    @Override
    public DoubleCollection values() {
        return values == null
                ? (values = TroveDoubleCollection.viewOf(map.valueCollection()))
                : values;
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    private final class Entry implements DoubleValueEntry<K> {

        private final K key;

        Entry(final K key) {
            this.key = key;
        }

        @Override
        public K key() {
            return key;
        }

        @Override
        public double doubleValue() {
            return map.get(key);
        }

    }

}
