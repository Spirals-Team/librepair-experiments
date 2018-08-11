package net.coljate.tree;

import net.coljate.map.MutableSortedMap;

/**
 *
 * @author Ollie
 */
public interface MutableSortedTreeMap<K, V, N extends MutableTreeMapNode<K, V, N>>
        extends MutableTreeMap<K, V, N>, SortedTreeMap<K, V, N>, MutableSortedMap<K, V> {

    @Override
    MutableSortedTreeMap<K, V, ?> mutableCopy();

}
