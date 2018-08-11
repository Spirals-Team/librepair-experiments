package net.coljate.map;

import java.util.Optional;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.coljate.Container;
import net.coljate.util.complexity.Complexity;
import net.coljate.util.complexity.TimeComplexity;

/**
 *
 * @author Ollie
 * @since 1.0
 * @see FastGet
 */
public interface Associative<K, V> extends Container {

    /**
     * Typed getter.
     *
     * @param key
     * @return
     */
    @TimeComplexity(bestCase = Complexity.CONSTANT)
    @CheckForNull
    default V get(@Nullable final K key) {
        return this.getIfPresent(key);
    }

    /**
     * Untyped getter.
     *
     * @param key
     * @return the value, or {@code null} if there is no association.
     */
    @CheckForNull
    V getIfPresent(@Nullable Object key);

    @Nonnull
    default Optional<V> maybeGet(final K key) {
        return Optional.ofNullable(this.get(key));
    }

}
