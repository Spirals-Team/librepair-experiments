package net.coljate.tree;

import javax.annotation.CheckForNull;

import net.coljate.Container;

/**
 * Some set of connected {@link TreeNode nodes}.
 *
 * @author Ollie
 */
public interface Tree<N extends TreeNode<N>> extends Container {

    @CheckForNull
    N root();

    @Override
    default boolean isEmpty() {
        return this.root() == null;
    }

}
