package net.coljate.graph;

import net.coljate.set.AbstractSet;

/**
 *
 * @author Ollie
 */
public abstract class AbstractGraph<V, E> extends AbstractSet<Relationship<V, E>> implements Graph<V, E> {

    @Override
    public boolean contains(final Object object) {
        return Graph.super.contains(object);
    }

}
