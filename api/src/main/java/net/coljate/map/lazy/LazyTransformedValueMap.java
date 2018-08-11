package net.coljate.map.lazy;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;

import net.coljate.collection.Collection;
import net.coljate.collection.lazy.LazyCollection;
import net.coljate.collection.lazy.LazyTransformedCollection;
import net.coljate.map.Entry;
import net.coljate.map.ImmutableEntry;
import net.coljate.map.Map;
import net.coljate.set.Set;
import net.coljate.util.iterator.Iterators;

/**
 *
 * @author Ollie
 */
public class LazyTransformedValueMap<K, V1, V2> implements LazyMap<K, V2> {

    public static <K, V1, V2> Function<Collection<Entry<K, V1>>, LazyTransformedValueMap<K, V1, V2>> transform(final Function<? super V1, ? extends V2> transformation) {
        return collection -> {
            final Map<K, V1> map = Map.copyOrCast(collection);
            return new LazyTransformedValueMap<>(map, transformation);
        };
    }

    private final Map<K, V1> map;
    private final Function<? super V1, ? extends V2> transformation;
    private LazyCollection<V2> values;

    public LazyTransformedValueMap(
            final Map<K, V1> map,
            final Function<? super V1, ? extends V2> transformation) {
        this.map = Objects.requireNonNull(map, "map");
        this.transformation = Objects.requireNonNull(transformation, "transformation");
    }

    @Override
    public Entry<K, V2> getEntry(final Object key) {
        return this.transform(map.getEntry(key));
    }

    private Entry<K, V2> transform(final Entry<K, V1> entry) {
        return entry == null
                ? null
                : ImmutableEntry.of(entry.key(), transformation.apply(entry.value()));
    }

    @Override
    public Set<K> keys() {
        return map.keys();
    }

    @Override
    public LazyCollection<V2> values() {
        return values == null
                ? (values = new LazyTransformedCollection<>(map.values(), transformation))
                : values;
    }

    @Override
    public Iterator<Entry<K, V2>> iterator() {
        return Iterators.transform(map.iterator(), this::transform);
    }

}
