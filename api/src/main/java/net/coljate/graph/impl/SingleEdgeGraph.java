package net.coljate.graph.impl;

import net.coljate.collection.ImmutableCollection;
import net.coljate.graph.AbstractGraph;
import net.coljate.graph.ImmutableGraph;
import net.coljate.set.ImmutableSet;

/**
 *
 * @author ollie
 */
public abstract class SingleEdgeGraph<V, E>
        extends AbstractGraph<V, E>
        implements ImmutableGraph<V, E> {

    private final V from, to;
    private final E edge;

    public SingleEdgeGraph(final V from, final V to, final E edge) {
        this.from = from;
        this.to = to;
        this.edge = edge;
    }

    protected V from() {
        return from;
    }

    protected V to() {
        return to;
    }

    protected E edge() {
        return edge;
    }

    @Override
    public ImmutableSet<V> vertices() {
        return ImmutableSet.of(from, to);
    }

    @Override
    public ImmutableCollection<E> edges() {
        return ImmutableSet.of(edge);
    }

}
