package net.coljate.tree.impl;

import net.coljate.tree.AbstractTreeMap;
import net.coljate.tree.ImmutableTreeMap;
import net.coljate.tree.ImmutableTreeNode;

/**
 *
 * @author Ollie
 */
public class SimpleImmutableTreeMap<K, V, N extends ImmutableTreeNode<K, V, N>>
        extends AbstractTreeMap<K, V, N>
        implements ImmutableTreeMap<K, V, N> {

    private final N root;

    public SimpleImmutableTreeMap(final N root) {
        this.root = root;
    }

    @Override
    public N root() {
        return root;
    }

    @Override
    public SimpleImmutableTreeMap<K, V, N> immutableCopy() {
        return this;
    }

    @Override
    public ImmutableTreeMap<K, V, ?> with(final K key, final V value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
