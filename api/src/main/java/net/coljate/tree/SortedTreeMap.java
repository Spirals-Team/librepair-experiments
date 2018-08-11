package net.coljate.tree;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

import javax.annotation.Nonnull;

import net.coljate.map.Entry;
import net.coljate.map.Map;
import net.coljate.map.SortedMap;
import net.coljate.set.AbstractSet;
import net.coljate.set.SequentialSet;
import net.coljate.tree.impl.RedBlackTreeMap;
import net.coljate.tree.navigation.TreeNavigation;
import net.coljate.util.iterator.Iterators;
import net.coljate.util.functions.Functions;

/**
 *
 * @author Ollie
 */
public interface SortedTreeMap<K, V, N extends TreeMapNode<K, V, N>>
        extends TreeMap<K, V, N>, SortedMap<K, V> {

    @Override
    N least();

    @Override
    N greatest();

    @Override
    default SequentialSet<K> keys() {
        return this.keys(TreeNavigation.getDefault());
    }

    @Override
    default SequentialSet<K> keys(final TreeNavigation navigation) {
        return new SortedTreeKeySet<>(this, navigation);
    }

    @Override
    default SortedTreeMap<K, V, N> filter(final Predicate<? super Entry<K, V>> predicate) {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    MutableSortedTreeMap<K, V, ?> mutableCopy();

    static <K, V> MutableSortedTreeMap<K, V, ?> create(@Nonnull final Comparator<? super Entry<K, V>> comparator) {
        return new RedBlackTreeMap<>(comparator);
    }

    static <K extends Comparable<? super K>, V> MutableSortedTreeMap<K, V, ?> createComparingKeys() {
        return RedBlackTreeMap.keyComparing();
    }

    static <K, V> MutableSortedTreeMap<K, V, ?> createComparingKeys(@Nonnull final Comparator<? super K> keyComparator) {
        return RedBlackTreeMap.keyComparing(keyComparator);
    }

    static <K, V> MutableSortedTreeMap<K, V, ?> copyOf(final SortedMap<K, V> map) {
        return RedBlackTreeMap.copyOf(map);
    }

    static <K, V> MutableSortedTreeMap<K, V, ?> copyOf(final Map<? extends K, ? extends V> map, final Comparator<? super Entry<K, V>> comparator) {
        return RedBlackTreeMap.copyOf(map, comparator);
    }

    class SortedTreeKeySet<K, N extends TreeMapNode<K, ?, N>>
            extends AbstractSet<K>
            implements SequentialSet<K> {

        private final SortedTreeMap<K, ?, N> tree;
        private final TreeNavigation navigation;

        protected SortedTreeKeySet(final SortedTreeMap<K, ?, N> tree, final TreeNavigation navigation) {
            this.tree = Objects.requireNonNull(tree);
            this.navigation = Objects.requireNonNull(navigation);
        }

        @Override
        public boolean contains(final Object object) {
            return tree.containsKey(object);
        }

        @Override
        public Iterator<K> iterator() {
            return Iterators.transform(tree.iterator(navigation), N::key);
        }

        @Override
        public K first() {
            return Functions.ifNonNull(tree.least(), N::key);
        }

        @Override
        public K last() {
            return Functions.ifNonNull(tree.greatest(), N::key);
        }

    }

}
