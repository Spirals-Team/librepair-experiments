package net.coljate.map.primitive;

import net.coljate.map.AbstractEntry;
import net.coljate.map.Entry;
import net.coljate.map.Map;

/**
 *
 * @author Ollie
 */
public interface LongKeyMap<V> extends Map<Long, V> {

    boolean containsKey(long key);

    V get(long key);

    @Override
    default boolean containsKey(final Object key) {
        return key instanceof Long && this.containsKey(((Long) key).longValue());
    }

    @Override
    default V get(final Long key) {
        return key == null ? null : this.get(key.longValue());
    }

    @Override
    default LongKeyEntry<V> getEntry(final Object key) {
        if (key instanceof Long) {
            final long k = (Long) key;
            final V value = this.get(k);
            return value == null && !this.containsKey(k)
                    ? null
                    : new UnmodifiableLongKeyEntry<>(k, value);
        }
        return null;
    }

    interface LongKeyEntry<V> extends Entry<Long, V> {

        long longKey();

        @Override
        @Deprecated
        default Long key() {
            return this.longKey();
        }

    }

    class UnmodifiableLongKeyEntry<V> extends AbstractEntry<Long, V> implements LongKeyEntry<V> {

        private final long key;
        private final V value;

        public UnmodifiableLongKeyEntry(final long key, final V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public long longKey() {
            return key;
        }

        @Override
        public V value() {
            return value;
        }

    }

}
