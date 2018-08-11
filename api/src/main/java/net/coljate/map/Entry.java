package net.coljate.map;

import java.util.Objects;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.coljate.map.impl.SimpleImmutableEntry;

/**
 *
 * @author Ollie
 * @see Map
 */
public interface Entry<K, V> {

    @CheckForNull
    K key();

    @CheckForNull
    V value();

    default boolean contains(@Nullable final Object key, @Nullable final Object value) {
        return Objects.equals(key, this.key()) && Objects.equals(value, this.value());
    }

    @Nonnull
    default ImmutableEntry<K, V> immutableCopy() {
        return new SimpleImmutableEntry<>(this.key(), this.value());
    }

    @Nonnull
    static <K, V> Entry<K, V> of(final K key, final V value) {
        return ImmutableEntry.of(key, value);
    }

    @SuppressWarnings("unchecked")
    static <K, V> Entry<K, V> copyOf(@Nonnull final Entry<? extends K, ? extends V> entry) {
        return entry instanceof ImmutableEntry
                ? (ImmutableEntry<K, V>) entry
                : of(entry.key(), entry.value());
    }

}
