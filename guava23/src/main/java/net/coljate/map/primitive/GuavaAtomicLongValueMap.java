package net.coljate.map.primitive;

import java.util.OptionalLong;
import java.util.function.LongSupplier;

import net.coljate.collection.Collection;
import net.coljate.set.Set;

/**
 *
 * @author Ollie
 */
public class GuavaAtomicLongValueMap<K> implements LongValueMap<K> {

    private final com.google.common.util.concurrent.AtomicLongMap<K> map;
    private final long defaultValue;

    public GuavaAtomicLongValueMap(final com.google.common.util.concurrent.AtomicLongMap<K> map) {
        this(map, 0L);
    }

    public GuavaAtomicLongValueMap(final com.google.common.util.concurrent.AtomicLongMap<K> map, final long defaultValue) {
        this.map = map;
        this.defaultValue = defaultValue;
    }

    @Override
    public long defaultValue() {
        return defaultValue;
    }

    @Override
    public boolean containsKey(final Object key) {
        return map.containsKey(key);
    }

    @Override
    public long getLong(final K key) {
        return defaultValue == 0L || map.containsKey(key)
                ? map.get(key)
                : map.updateAndGet(key, l -> defaultValue);
    }

    @Override
    public long getLong(final K key, final LongSupplier ifMissing) {
        return map.containsKey(key)
                ? map.get(key)
                : ifMissing.getAsLong();
    }

    @Override
    public OptionalLong maybeGetLong(final K key) {
        return map.containsKey(key)
                ? OptionalLong.of(map.get(key))
                : OptionalLong.empty();
    }

    @Override
    @SuppressWarnings({"unchecked", "element-type-mismatch"})
    public LongValueEntry<K> getEntry(final Object key) {
        final Long value = map.asMap().get(key);
        return value == null
                ? null
                : new UnmodifiableLongValueEntry<>((K) key, value);
    }

    @Override
    public Set<K> keys() {
        return Set.viewOf(map.asMap().keySet());
    }

    @Override
    public Collection<Long> values() {
        return Collection.viewOf(map.asMap().values());
    }

    @Override
    public long sumValues() {
        return map.sum();
    }

}
