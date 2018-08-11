package net.coljate.map.impl;

import java.util.Objects;

import javax.annotation.Nonnull;

import net.coljate.map.AbstractEntry;
import net.coljate.map.Entry;
import net.coljate.map.MutableBidirectionalMap;
import net.coljate.map.MutableEntry;
import net.coljate.map.MutableMap;
import net.coljate.util.functions.Functions;

/**
 *
 * @author Ollie
 */
public class MutableHashBidirectionalMap<K, V>
        extends HashBidirectionalMap<K, V, MutableHashBidirectionalMap<V, K>>
        implements MutableBidirectionalMap<K, V> {

    public static <K, V> MutableHashBidirectionalMap<K, V> create() {
        return new MutableHashBidirectionalMap<>(MutableMap.createHashMap());
    }

    public static <K, V> MutableHashBidirectionalMap<K, V> create(final Entry<? extends K, ? extends V> entry) {
        final MutableMap<K, V> map = MutableMap.createHashMap();
        map.put(entry);
        return new MutableHashBidirectionalMap<>(map);
    }

    private final MutableMap<K, V> forward;

    protected MutableHashBidirectionalMap(final MutableMap<K, V> forward) {
        this(forward, null);
    }

    public MutableHashBidirectionalMap(
            final MutableMap<K, V> forward,
            final MutableHashBidirectionalMap<V, K> inverse) {
        super(forward, inverse);
        this.forward = forward;
    }

    @Override
    protected MutableHashBidirectionalMap<V, K> createInverseView() {
        final MutableMap<V, K> inverse = MutableMap.createHashMap();
        forward.forEach((k, v) -> inverse.put(v, k));
        return new MutableHashBidirectionalMap<>(inverse, this);
    }

    @Override
    public MutableEntry<K, V> getEntry(final Object key) {
        return Functions.ifNonNull(forward.getEntry(key), MutableHashBidirectionalMapEntry::new);
    }

    @Override
    public V put(final K key, final V value) {
        //Check for no-op
        final Entry<K, V> current = forward.getEntry(key);
        if (current != null && current.contains(key, value)) {
            return value;
        }
        //Check for duplicate values
        final MutableBidirectionalMap<V, K> inverse = this.inverseView();
        final Entry<V, K> inverseEntry = inverse.getEntry(value);
        if (inverseEntry != null && !inverse.contains(value, key)) {
            throw new DuplicateValueException(value);
        }
        //Write
        final V previous = forward.put(key, value);
        if (!Objects.equals(value, previous)) {
            inverse.evict(previous);
        }
        inverse.put(value, key);
        return previous;
    }

    @Override
    public boolean remove(final Object key, final Object value) {
        if (this.containsKey(key)) {
            final boolean removed = forward.remove(key, value);
            this.inverseView().remove(value, key);
            return removed;
        } else {
            return false;
        }
    }

    @Override
    public V evict(final Object key) {
        if (this.containsKey(key)) {
            final V evicted = forward.evict(key);
            this.inverseView().evict(key);
            return evicted;
        }
        return null;
    }

    @Override
    public void clear() {
        if (!this.isEmpty()) {
            forward.clear();
            this.inverseView().clear();
        }
    }

    private final class MutableHashBidirectionalMapEntry
            extends AbstractEntry<K, V>
            implements MutableEntry<K, V> {

        private final MutableEntry<K, V> forward;

        MutableHashBidirectionalMapEntry(@Nonnull final MutableEntry<K, V> forward) {
            this.forward = Objects.requireNonNull(forward);
        }

        @Override
        public K key() {
            return forward.key();
        }

        @Override
        public V value() {
            return forward.value();
        }

        @Override
        public void setValue(final V value) {
            MutableHashBidirectionalMap.this.put(this.key(), value);
        }

    }

}
