package net.coljate.tree.navigation;

import net.coljate.list.List;
import net.coljate.list.MutableList;
import net.coljate.tree.BinaryTreeMapNode;
import net.coljate.tree.Tree;
import net.coljate.tree.TreeNode;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author Ollie
 * @since 1.0
 */
public interface TreeNavigation {

    @CheckForNull
    default <N extends TreeNode<N>> N first(final Tree<N> tree, final Predicate<? super N> predicate) {
        return this.first(tree.root(), predicate);
    }

    /**
     * @param <N>
     * @param node
     * @param predicate
     * @return
     */
    @CheckForNull
    <N extends TreeNode<N>> N first(N node, Predicate<? super N> predicate);

    /**
     * @param <N>
     * @param tree
     * @param predicate
     * @return
     */
    @Nonnull
    default <N extends TreeNode<N>> List<N> collect(final Tree<N> tree, final Predicate<? super N> predicate) {
        return this.collect(tree.root(), predicate);
    }

    default <N extends TreeNode<N>> List<N> collect(final N root, final Predicate<? super N> predicate) {
        final MutableList<N> nodes = MutableList.create(10);
        this.collect(root, nodes::suffix, predicate);
        return nodes;
    }

    /**
     * @param <N>
     * @param node
     * @param nodes
     */
    default <N extends TreeNode<N>> void collect(final N node, final Consumer<? super N> nodes) {
        this.collect(node, nodes, n -> true);
    }

    /**
     * @param <N>
     * @param node
     * @param nodes
     * @param predicate
     */
    <N extends TreeNode<N>> void collect(N node, Consumer<? super N> nodes, Predicate<? super N> predicate);

    /**
     * @param <N>
     * @param node
     * @param nodes
     */
    default <N extends BinaryTreeMapNode<?, ?, N>> void collectBinaryNodes(
            final N node,
            final Consumer<? super N> nodes) {
        this.collectBinaryNodes(node, nodes, n -> true);
    }

    /**
     * @param <N>
     * @param node
     * @param nodes
     * @param predicate
     */
    default <N extends BinaryTreeMapNode<?, ?, N>> void collectBinaryNodes(
            final N node,
            final Consumer<? super N> nodes,
            final Predicate<? super N> predicate) {
        this.collect(node, nodes, predicate);
    }

    TreeNavigation DEPTH_FIRST_RECURSIVE = new DepthFirstRecursiveTreeNavigation();
    TreeNavigation BREADTH_FIRST_RECURSIVE = new BreadthFirstRecursiveTreeNavigation();
    AtomicReference<PrivateContainer> DEFAULT = new AtomicReference<>(new PrivateContainer(DEPTH_FIRST_RECURSIVE));

    static TreeNavigation getDefault() {
        return DEFAULT.get().navigation;
    }

    static void setDefault(final TreeNavigation navigation) {
        DEFAULT.set(new PrivateContainer(navigation));
    }

    class PrivateContainer {

        private final TreeNavigation navigation;

        private PrivateContainer(final TreeNavigation navigation) {
            Objects.requireNonNull(navigation, "Cannot set null default tree navigation!");
            this.navigation = navigation;
        }

    }

}
