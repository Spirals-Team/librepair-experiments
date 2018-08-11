package net.coljate.tree;

import net.coljate.map.AbstractMap;

/**
 *
 * @author Ollie
 * @since 1.0
 */
public abstract class AbstractTreeMap<K, V, N extends TreeMapNode<K, V, N>>
        extends AbstractMap<K, V>
        implements TreeMap<K, V, N> {

}
