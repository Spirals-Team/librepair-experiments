package net.coljate.tree;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.CheckForNull;

import net.coljate.set.Set;
import net.coljate.tree.navigation.TreeNavigation;

/**
 *
 * @author Ollie
 * @since 1.0
 */
public interface BinaryTreeMapNode<K, V, N extends BinaryTreeMapNode<K, V, N>> extends TreeMapNode<K, V, N> {

    @CheckForNull
    N left();

    @CheckForNull
    N right();

    @Override
    default Set<? extends N> children() {
        return Set.ofNonNull(this.left(), this.right());
    }

    @Override
    default boolean hasChildren() {
        return this.left() != null && this.right() != null;
    }

    default boolean isLeaf() {
        return this.left() == null
                && this.right() == null;
    }

    @Override
    default int countDescendents(final TreeNavigation treeNavigation) {
        final AtomicInteger counter = new AtomicInteger(1);
        treeNavigation.collectBinaryNodes((BinaryTreeMapNode) this, node -> counter.incrementAndGet());
        return counter.get();
    }

    @CheckForNull
    default N leftMost() {
        N current = null, left = this.left();
        while (left != null) {
            current = left;
            left = left.left();
        }
        return current;
    }

    @CheckForNull
    default N rightMost() {
        N current = null, right = this.right();
        while (right != null) {
            current = right;
            right = right.right();
        }
        return current;
    }

}
