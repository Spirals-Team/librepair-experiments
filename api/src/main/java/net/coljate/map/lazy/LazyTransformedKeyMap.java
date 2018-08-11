package net.coljate.map.lazy;

import java.util.Objects;
import java.util.function.Function;

import javax.annotation.Nonnull;

import net.coljate.collection.Collection;
import net.coljate.map.Entry;
import net.coljate.set.Set;

/**
 *
 * @author Ollie
 */
public class LazyTransformedKeyMap<K, V>
        implements LazyMap<K, V> {

    private final Set<? extends K> keys;
    private final Function<? super K, ? extends V> toValue;

    public LazyTransformedKeyMap(
            @Nonnull final Set<? extends K> keys,
            @Nonnull final Function<? super K, ? extends V> toValue) {
        this.keys = Objects.requireNonNull(keys);
        this.toValue = Objects.requireNonNull(toValue);
    }

    @Override
    public Set<K> keys() {
        return Set.viewOf(keys);
    }

    @Override
    public Collection<V> values() {
        return keys.transform(toValue);
    }

    @Override
    public Entry<K, V> getEntry(final Object object) {
        if (keys.contains(object)) {
            final K key = (K) object;
            return Entry.of(key, toValue.apply(key));
        } else {
            return null;
        }
    }

}
