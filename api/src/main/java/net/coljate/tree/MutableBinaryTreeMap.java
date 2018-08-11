package net.coljate.tree;

import net.coljate.tree.MutableBinaryTreeMap.MutableBinaryNode;

/**
 * Mutable binary tree.
 *
 * @author Ollie
 */
public interface MutableBinaryTreeMap<K, V, N extends MutableBinaryNode<K, V, N>>
        extends BinaryTreeMap<K, V, N>, MutableTreeMap<K, V, N> {

    /**
     * Mutable node that allows updating of the value.
     *
     * @param <K>
     * @param <V>
     */
    interface MutableBinaryNode<K, V, N extends MutableBinaryNode<K, V, N>>
            extends BinaryTreeMapNode<K, V, N>, MutableTreeMapNode<K, V, N> {

    }

}
