package net.coljate.map.lazy;

import java.util.Comparator;
import java.util.function.Predicate;

import net.coljate.map.Entry;
import net.coljate.map.KeySortedMap;

/**
 *
 * @author Ollie
 */
public class LazyFilteredKeySortedMap<K, V>
        extends LazyFilteredSortedMap<K, V>
        implements KeySortedMap<K, V> {

    private final KeySortedMap<K, V> map;

    public LazyFilteredKeySortedMap(
            final KeySortedMap<K, V> map,
            final Predicate<? super Entry<K, V>> predicate) {
        super(map, predicate);
        this.map = map;
    }

    @Override
    public Comparator<? super K> keyComparator() {
        return map.keyComparator();
    }

    @Override
    public KeySortedMap<K, V> filterKeys(final Predicate<? super K> keyPredicate) {
        return new LazyFilteredKeySortedMap<>(this, e -> keyPredicate.test(e.key()));
    }

    @Override
    public KeySortedMap<K, V> filter(final Predicate<? super Entry<K, V>> predicate) {
        return new LazyFilteredKeySortedMap<>(this, predicate);
    }

}
