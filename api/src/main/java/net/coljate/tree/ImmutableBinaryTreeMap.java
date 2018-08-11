package net.coljate.tree;

import net.coljate.set.ImmutableSet;
import net.coljate.tree.ImmutableBinaryTreeMap.ImmutableBinaryNode;

/**
 *
 * @author Ollie
 */
public interface ImmutableBinaryTreeMap<K, V, N extends ImmutableBinaryNode<K, V, N>>
        extends BinaryTreeMap<K, V, N>, ImmutableTreeMap<K, V, N> {

    @Override
    @Deprecated
    default ImmutableBinaryTreeMap<K, V, N> immutableCopy() {
        return this;
    }

    interface ImmutableBinaryNode<K, V, N extends ImmutableBinaryNode<K, V, N>>
            extends BinaryTreeMapNode<K, V, N>, ImmutableTreeNode<K, V, N> {

        @Override
        public default ImmutableSet<? extends N> children() {
            return BinaryTreeMapNode.super.children().immutableCopy();
        }

        @Override
        @Deprecated
        default ImmutableBinaryNode<K, V, N> immutableCopy() {
            return this;
        }

    }

}
