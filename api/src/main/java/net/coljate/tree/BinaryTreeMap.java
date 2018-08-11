package net.coljate.tree;

/**
 *
 * @author Ollie
 */
public interface BinaryTreeMap<K, V, N extends BinaryTreeMapNode<K, V, N>>
        extends TreeMap<K, V, N> {

    @Override
    MutableBinaryTreeMap<K, V, ?> mutableCopy();

    @Override
    ImmutableBinaryTreeMap<K, V, ?> immutableCopy();

}
