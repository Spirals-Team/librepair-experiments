package net.coljate.tree.impl;

import net.coljate.map.AbstractEntry;
import net.coljate.set.ImmutableSet;
import net.coljate.tree.ImmutableTreeNode;

/**
 *
 * @author Ollie
 */
public class SimpleImmutableNode<K, V>
        extends AbstractEntry<K, V>
        implements ImmutableTreeNode<K, V, SimpleImmutableNode<K, V>> {

    public static <K, V> SimpleImmutableNode<K, V> leaf(final K key, final V value) {
        return new SimpleImmutableNode<>(key, value, ImmutableSet.of());
    }

    private final K key;
    private final V value;
    private final ImmutableSet<? extends SimpleImmutableNode<K, V>> children;

    public SimpleImmutableNode(K key, V value, ImmutableSet<? extends SimpleImmutableNode<K, V>> children) {
        this.key = key;
        this.value = value;
        this.children = children;
    }

    @Override
    public K key() {
        return key;
    }

    @Override
    public V value() {
        return value;
    }

    @Override
    public ImmutableSet<? extends SimpleImmutableNode<K, V>> children() {
        return children;
    }

}
