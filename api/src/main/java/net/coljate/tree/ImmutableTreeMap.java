package net.coljate.tree;

import net.coljate.collection.ImmutableCollection;
import net.coljate.map.Entry;
import net.coljate.map.ImmutableMap;
import net.coljate.set.ImmutableSet;
import net.coljate.util.iterator.UnmodifiableCovariantIterator;

/**
 *
 * @author Ollie
 * @since 1.0
 */
public interface ImmutableTreeMap<K, V, N extends ImmutableTreeNode<K, V, N>>
        extends TreeMap<K, V, N>, ImmutableMap<K, V> {

    @Override
    default N getEntry(final Object key) {
        return TreeMap.super.getEntry(key);
    }

    @Override
    ImmutableTreeMap<K, V, ?> with(K key, V value);

    @Override
    default ImmutableSet<K> keys() {
        return TreeMap.super.keys().immutableCopy();
    }

    @Override
    default ImmutableCollection<V> values() {
        return TreeMap.super.values().immutableCopy();
    }

    @Override
    default UnmodifiableCovariantIterator<Entry<K, V>, ? extends N> iterator() {
        return UnmodifiableCovariantIterator.wrap(TreeMap.super.iterator());
    }

    @Override
    @Deprecated
    default ImmutableTreeMap<K, V, N> immutableCopy() {
        return this;
    }

}
