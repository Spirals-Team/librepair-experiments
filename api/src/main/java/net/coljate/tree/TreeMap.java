package net.coljate.tree;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

import net.coljate.collection.Collection;
import net.coljate.map.Entry;
import net.coljate.map.Map;
import net.coljate.set.Set;
import net.coljate.tree.navigation.TreeNavigation;
import net.coljate.util.iterator.CovariantIterator;

/**
 * A {@link #root rooted} tree.
 *
 * @author Ollie
 * @since 1.0
 */
public interface TreeMap<K, V, N extends TreeMapNode<K, V, N>>
        extends Map<K, V>, Tree<N> {

    @Override
    N root();

    @Override
    default N getEntry(final Object key) {
        return this.getEntry(key, TreeNavigation.getDefault());
    }

    default N getEntry(final Object key, final TreeNavigation navigation) {
        return navigation.first(this.root(), node -> Objects.equals(key, node.key()));
    }

    default Collection<N> entries(final TreeNavigation navigation) {
        return navigation.collect(this.root(), node -> true);
    }

    @Override
    default Set<K> keys() {
        return this.keys(TreeNavigation.getDefault());
    }

    default Set<K> keys(final TreeNavigation navigation) {
        return this.entries(navigation)
                .transform(N::key)
                .distinct();
    }

    @Override
    default boolean isEmpty() {
        return Tree.super.isEmpty();
    }

    @Override
    default Collection<V> values() {
        return this.values(TreeNavigation.getDefault());
    }

    default Collection<V> values(final TreeNavigation navigation) {
        return this.entries(navigation).transform(N::value);
    }

    @Override
    default CovariantIterator<Entry<K, V>, ? extends N> iterator() {
        return CovariantIterator.of(this.iterator(TreeNavigation.getDefault()));
    }

    default Iterator<N> iterator(final TreeNavigation navigation) {
        return this.entries(navigation).iterator();
    }

    @Override
    default MutableTreeMap<K, V, ?> mutableCopy() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    default ImmutableTreeMap<K, V, ?> immutableCopy() {
        throw new UnsupportedOperationException();
    }

    @Override
    default TreeMap<K, V, N> filter(Predicate<? super Entry<K, V>> predicate) {
        throw new UnsupportedOperationException(); //TODO lazy evaluation
    }

}
