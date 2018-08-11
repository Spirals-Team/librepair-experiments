package net.coljate.map.lazy;

import net.coljate.collection.Collection;
import net.coljate.map.Entry;
import net.coljate.map.Map;
import net.coljate.set.Set;

/**
 *
 * @author Ollie
 */
public class LazyUnionMap<K, V> implements LazyMap<K, V> {

    public static <K, V> Map<K, V> of(final Map<K, V> left, final Map<? extends K, ? extends V> right) {
        if (right.isAlwaysEmpty()) {
            return left;
        }
        return new LazyUnionMap<>(left, right);
    }

    private final Map<K, V> left;
    private final Map<? extends K, ? extends V> right;

    protected LazyUnionMap(final Map<K, V> left, final Map<? extends K, ? extends V> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Set<K> keys() {
        return left.keys().union(right.keys());
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public Entry<K, V> getEntry(final Object key) {
        final Entry<K, V> l = left.getEntry(key);
        if (l != null) {
            return l;
        }
        final Entry<? extends K, ? extends V> r = right.getEntry(key);
        return r == null
                ? null
                : Entry.copyOf(r);
    }

}
