package net.coljate.map.impl;

import com.hazelcast.core.IMap;
import com.hazelcast.projection.Projection;

import java.io.Serializable;
import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Nonnull;

import net.coljate.collection.Collection;
import net.coljate.set.Set;

/**
 *
 * @author ollie
 */
public class HazelcastMap<K, V> extends ConcurrentWrappedMap<K, V> {

    private final IMap<K, V> delegate;

    public HazelcastMap(final IMap<K, V> delegate) {
        super(delegate);
        this.delegate = delegate;
    }

    @Nonnull
    public Set<K> localKeys() {
        return Set.viewOf(delegate.localKeySet());
    }

    @Override
    public <T> Collection<T> transform(final BiFunction<? super K, ? super V, ? extends T> transform) {
        return transform instanceof Serializable
                ? Collection.viewOf(delegate.project(new BiFunctionProjection<>(transform)))
                : super.transform(transform);
    }

    private static class BiFunctionProjection<K, V, R> extends Projection<java.util.Map.Entry<K, V>, R> {

        private static final long serialVersionUID = 1L;

        private final BiFunction<? super K, ? super V, ? extends R> function;

        BiFunctionProjection(final BiFunction<? super K, ? super V, ? extends R> function) {
            this.function = function;
        }

        @Override
        public R transform(final Map.Entry<K, V> input) {
            return function.apply(input.getKey(), input.getValue());
        }

    }

}
