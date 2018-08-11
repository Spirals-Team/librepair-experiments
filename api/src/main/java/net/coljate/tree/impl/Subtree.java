package net.coljate.tree.impl;

import java.util.function.Supplier;

import net.coljate.map.Entry;
import net.coljate.map.ImmutableEntry;
import net.coljate.set.Set;
import net.coljate.tree.AbstractTreeMap;
import net.coljate.util.SelfTyped;
import net.coljate.tree.TreeMap;
import net.coljate.tree.MutableTreeMap;
import net.coljate.tree.TreeMapNode;

/**
 *
 * @author Ollie
 */
public class Subtree<K, V, T extends Subtree<K, V, T>>
        extends AbstractTreeMap<K, V, T>
        implements TreeMap<K, V, T>, TreeMapNode<K, V, T>, SelfTyped<T> {

    private final K key;
    private final Supplier<? extends V> getValue;
    private final Set<? extends T> children;

    public Subtree(final K key, final Supplier<? extends V> getValue, final Set<? extends T> children) {
        this.key = key;
        this.getValue = getValue;
        this.children = children;
    }

    @Override
    public K key() {
        return key;
    }

    @Override
    public V value() {
        return getValue.get();
    }

    public Entry<K, V> asEntry() {
        return ImmutableEntry.of(this.key(), this.value());
    }

    @Override
    public T root() {
        return this.self();
    }

    @Override
    public Set<? extends T> children() {
        return children;
    }

    @Override
    public boolean contains(final Object key, final Object value) {
        return TreeMapNode.super.contains(key, value);
    }

    @Override
    public MutableTreeMap<K, V, ?> mutableCopy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ImmutableSubtree<K, V> immutableCopy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean equals(final Object object) {
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Subtrees cannot be hashed.");
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ":[" + this.key() + ":" + this.value() + "]:" + children;
    }

}
