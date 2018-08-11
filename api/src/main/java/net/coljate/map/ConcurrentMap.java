package net.coljate.map;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;

import net.coljate.map.impl.ConcurrentWrappedHashMap;
import net.coljate.set.ConcurrentSet;

/**
 *
 * @author Ollie
 * @see java.util.concurrent.ConcurrentMap
 */
public interface ConcurrentMap<K, V>
        extends MutableMap<K, V>, ConcurrentSet<Entry<K, V>> {

    @Override
    default ConcurrentMap<K, V> mutableCopy() {
        return ConcurrentWrappedHashMap.copyOf(this);
    }

    @Override
    V putIfAbsent(K key, V value);

    @Override
    V computeIfAbsent(K key, Function<? super K, ? extends V> supplier);

    @Override
    boolean replace(K key, V expectedValue, V replacementValue);

    @Override
    boolean removeKey(Object key);

    @Override
    V evict(Object key);

    @Override
    default Spliterator<Entry<K, V>> spliterator() {
        return Spliterators.spliterator(this.iterator(), this.count(), Spliterator.SIZED | Spliterator.DISTINCT | Spliterator.NONNULL | Spliterator.CONCURRENT);
    }

    static <K, V> ConcurrentMap<K, V> createHashMap() {
        return ConcurrentWrappedHashMap.create();
    }

    static <K, V> ConcurrentMap<K, V> createHashMap(final int initialCapacity) {
        return ConcurrentWrappedHashMap.create(initialCapacity);
    }

}
