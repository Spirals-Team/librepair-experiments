package net.coljate.tree.impl;

import java.util.function.Predicate;

import net.coljate.map.Entry;
import net.coljate.tree.AbstractTreeMap;
import net.coljate.tree.ImmutableTreeMap;
import net.coljate.tree.ImmutableTreeNode;

/**
 *
 * @author Ollie
 */
public class EmptyTreeMap<K, V>
        extends AbstractTreeMap<K, V, SimpleImmutableNode<K, V>>
        implements ImmutableTreeMap<K, V, SimpleImmutableNode<K, V>> {

    @Override
    public SimpleImmutableNode<K, V> root() {
        return null;
    }

    @Override
    public SimpleImmutableTreeMap<K, V, SimpleImmutableNode<K, V>> with(final K key, final V value) {
        return new SimpleImmutableTreeMap<>(ImmutableTreeNode.leaf(key, value));
    }

    @Override
    public EmptyTreeMap<K, V> filter(final Predicate<? super Entry<K, V>> predicate) {
        return this;
    }

}
