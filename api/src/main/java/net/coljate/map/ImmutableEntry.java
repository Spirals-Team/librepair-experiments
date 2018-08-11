package net.coljate.map;

import net.coljate.map.impl.SimpleImmutableEntry;

/**
 *
 * @author Ollie
 */
public interface ImmutableEntry<K, V> extends Entry<K, V> {

    @Override
    @Deprecated
    default ImmutableEntry<K, V> immutableCopy() {
        return this;
    }

    static <K, V> ImmutableEntry<K, V> of(final K key, final V value) {
        return new SimpleImmutableEntry<>(key, value);
    }

}
