package net.coljate.map.primitive;

import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.LongSupplier;

import javax.annotation.Nonnull;

import net.coljate.map.Entry;
import net.coljate.map.Map;

/**
 *
 * @author Ollie
 */
public interface LongValueMap<K> extends Map<K, Long> {

    default long defaultValue() {
        return 0L;
    }

    /**
     *
     * @param key
     * @return the long associated with the given key, or the {@link #defaultValue()} if there is no association.
     */
    default long getLong(final K key) {
        return this.getLong(key, this::defaultValue);
    }

    long getLong(K key, LongSupplier ifMissing);

    @Nonnull
    OptionalLong maybeGetLong(K key);

    @Override
    LongValueEntry<K> getEntry(Object key);

    @Override
    @Deprecated
    default Optional<Long> maybeGet(final K key) {
        final OptionalLong optional = this.maybeGetLong(key);
        return optional.isPresent()
                ? Optional.of(optional.getAsLong())
                : Optional.empty();
    }

    default long sumValues() {
        return this.values().serialStream().mapToLong(Long::valueOf).sum();
    }

    interface LongValueEntry<K> extends Entry<K, Long> {

        long longValue();

        @Override
        @Deprecated
        default Long value() {
            return this.longValue();
        }

    }

    class UnmodifiableLongValueEntry<K> implements LongValueEntry<K> {

        private final K key;
        private final long value;

        public UnmodifiableLongValueEntry(final K key, final long value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K key() {
            return key;
        }

        @Override
        public long longValue() {
            return value;
        }

    }

}
