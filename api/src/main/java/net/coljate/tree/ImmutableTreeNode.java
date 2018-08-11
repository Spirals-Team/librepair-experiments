package net.coljate.tree;

import net.coljate.map.ImmutableEntry;
import net.coljate.set.ImmutableSet;
import net.coljate.tree.impl.SimpleImmutableNode;

/**
 *
 * @author Ollie
 */
public interface ImmutableTreeNode<K, V, N extends ImmutableTreeNode<K, V, N>>
        extends TreeMapNode<K, V, N>, ImmutableEntry<K, V> {

    @Override
    ImmutableSet<? extends N> children();

    static <K, V> SimpleImmutableNode<K, V> leaf(final K key, final V value) {
        return SimpleImmutableNode.leaf(key, value);
    }

}
