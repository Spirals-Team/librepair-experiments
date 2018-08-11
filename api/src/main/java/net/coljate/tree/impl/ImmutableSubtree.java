package net.coljate.tree.impl;

import net.coljate.map.Entry;
import net.coljate.set.ImmutableSet;
import net.coljate.tree.ImmutableTreeMap;
import net.coljate.tree.ImmutableTreeNode;

/**
 *
 * @author Ollie
 */
public class ImmutableSubtree<K, V>
        extends Subtree<K, V, ImmutableSubtree<K, V>>
        implements ImmutableTreeMap<K, V, ImmutableSubtree<K, V>>, ImmutableTreeNode<K, V, ImmutableSubtree<K, V>> {

    public static <K, V> ImmutableSubtree<K, V> of(final Entry<? extends K, ? extends V> entry) {
        return of(entry.key(), entry.value());
    }

    public static <K, V> ImmutableSubtree<K, V> of(final K key, final V value) {
        return new ImmutableSubtree<>(key, value, ImmutableSet.of());
    }

    @SafeVarargs
    public static <K, V> ImmutableSubtree<K, V> of(final K key, final V value, final ImmutableSubtree<K, V>... children) {
        return new ImmutableSubtree<>(key, value, ImmutableSet.of(children));
    }

    protected ImmutableSubtree(
            final K key,
            final V value,
            final ImmutableSet<? extends ImmutableSubtree<K, V>> children) {
        super(key, () -> value, children);
    }

    @Override
    public ImmutableSubtree<K, V> root() {
        return this;
    }

    @Override
    public ImmutableSet<? extends ImmutableSubtree<K, V>> children() {
        return super.children().immutableCopy();
    }

    @Override
    public ImmutableSubtree<K, V> with(final K key, final V value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Deprecated
    public ImmutableSubtree<K, V> immutableCopy() {
        return this;
    }

}
