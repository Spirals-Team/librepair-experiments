package net.coljate.map;

import java.util.function.Predicate;

import javax.annotation.CheckForNull;

import net.coljate.map.lazy.LazyFilteredSortedMap;
import net.coljate.set.SequentialSet;
import net.coljate.set.SortedSet;
import net.coljate.tree.impl.RedBlackTreeMap;
import net.coljate.util.functions.Predicates;
import net.coljate.util.suppliers.Suppliers;

/**
 * Map sorted by entries.
 *
 * @author Ollie
 * @see KeySortedMap
 * @see java.util.SortedMap
 * @see java.util.NavigableMap
 */
public interface SortedMap<K, V> extends SortedSet<Entry<K, V>>, Map<K, V> {

    @Override
    SequentialSet<K> keys();

    @Override
    default SortedMap<K, V> filter(final Predicate<? super Entry<K, V>> predicate) {
        return new LazyFilteredSortedMap<>(this, predicate);
    }

    @Override
    default SortedMap<K, V> greaterThan(final Entry<K, V> entry, final boolean orEqual) {
        return this.filter(Predicates.greaterThan(entry, orEqual, this.comparator()));
    }

    @Override
    default SortedMap<K, V> lessThan(final Entry<K, V> entry, final boolean orEqual) {
        return this.filter(Predicates.lessThan(entry, orEqual, this.comparator()));
    }

    @CheckForNull
    default Entry<K, V> ceilingEntry(final K key) {
        final Entry<K, V> entry = Suppliers.firstNonNull(this.getEntry(key), () -> Entry.of(key, null));
        return this.greaterThan(entry, true).least();
    }

    @Override
    default MutableSortedMap<K, V> mutableCopy() {
        return RedBlackTreeMap.copyOf(this);
    }

}
