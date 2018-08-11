package net.coljate.tree;

import net.coljate.map.Entry;

/**
 *
 * @author Ollie
 */
public interface TreeMapNode<K, V, N extends TreeMapNode<K, V, N>>
        extends Entry<K, V>, TreeNode<N> {

}
