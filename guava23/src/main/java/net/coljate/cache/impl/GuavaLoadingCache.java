package net.coljate.cache.impl;

import java.util.function.Function;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import net.coljate.cache.ConcurrentCache;
import net.coljate.map.ConcurrentMap;
import net.coljate.map.impl.MutableWrappedMap;

/**
 *
 * @author Ollie
 */
@SuppressWarnings("element-type-mismatch")
public class GuavaLoadingCache<K, V>
        extends MutableWrappedMap<K, V>
        implements ConcurrentCache<K, V> {

    public static <K, V> GuavaLoadingCache<K, V> create(final Function<? super K, ? extends V> valueFunction) {
        return create(CacheBuilder.newBuilder(), valueFunction);
    }

    public static <K, V> GuavaLoadingCache<K, V> create(final CacheBuilder<? super K, ? super V> builder, final Function<? super K, ? extends V> valueFunction) {
        return new GuavaLoadingCache<>(builder.build(new CacheLoader<K, V>() {

            @Override
            public V load(final K key) throws Exception {
                return valueFunction.apply(key);
            }

        }));
    }

    private final LoadingCache<K, V> cache;

    protected GuavaLoadingCache(final LoadingCache<K, V> cache) {
        super(cache.asMap());
        this.cache = cache;
    }

    @Override
    public V get(final K key) {
        return cache.getUnchecked(key);
    }

    @Override
    public V getIfPresent(final Object key) {
        return cache.getIfPresent(key);
    }

    @Override
    public void clear() {
        cache.invalidateAll();
    }

    @Override
    public ConcurrentMap<K, V> mutableCopy() {
        return ConcurrentCache.super.mutableCopy();
    }

}
