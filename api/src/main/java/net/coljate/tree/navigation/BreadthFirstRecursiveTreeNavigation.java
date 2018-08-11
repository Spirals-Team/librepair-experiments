package net.coljate.tree.navigation;

import net.coljate.list.MutableList;
import net.coljate.tree.TreeNode;

import javax.annotation.CheckForNull;
import java.util.function.Consumer;
import java.util.function.Predicate;

class BreadthFirstRecursiveTreeNavigation implements TreeNavigation {

    @CheckForNull
    @Override
    public <N extends TreeNode<N>> N first(final N node, final Predicate<? super N> predicate) {
        if (node == null || predicate.test(node)) {
            return node;
        }
        return this.first(node.children(), predicate);
    }

    private <N extends TreeNode<N>> N first(final Iterable<? extends N> nodes, final Predicate<? super N> predicate) {
        final MutableList<N> children = MutableList.create(10);
        for (final N node : nodes) {
            if (node != null) {
                if (predicate.test(node)) {
                    return node;
                }
                children.addAll(node.children());
            }
        }
        return this.first(children, predicate);
    }

    @Override
    public <N extends TreeNode<N>> void collect(final N node, final Consumer<? super N> nodes, final Predicate<? super N> predicate) {
        if (node != null) {
            if (predicate.test(node)) {
                nodes.accept(node);
            }
        }
        throw new UnsupportedOperationException(); //TODO

    }

}
