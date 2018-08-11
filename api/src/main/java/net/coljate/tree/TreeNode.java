package net.coljate.tree;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import net.coljate.set.Set;
import net.coljate.tree.navigation.TreeNavigation;
import net.coljate.util.SelfTyped;

/**
 *
 * @author Ollie
 */
public interface TreeNode<N extends TreeNode<N>> extends SelfTyped<N> {

    @Nonnull
    Set<? extends N> children();

    default boolean hasChildren() {
        return !this.children().isEmpty();
    }

    default int countDescendents() {
        return this.countDescendents(TreeNavigation.getDefault());
    }

    default int countDescendents(final TreeNavigation treeNavigation) {
        final AtomicInteger counter = new AtomicInteger(1);
        final Consumer<? super N> consumer = node -> counter.incrementAndGet();
        treeNavigation.collect(this.self(), consumer);
        return counter.get();
    }

}
