package net.coljate.graph.impl;

import net.coljate.graph.AbstractDirectedRelationship;

/**
 *
 * @author Ollie
 */
public class RegularDirectedRelationship<V, E>
        extends AbstractDirectedRelationship<V, E> {

    private final V from, to;
    private final E edge;

    public RegularDirectedRelationship(final V from, final V to, final E edge) {
        this.from = from;
        this.to = to;
        this.edge = edge;
    }

    @Override
    public V from() {
        return from;
    }

    @Override
    public V to() {
        return to;
    }

    @Override
    public E edge() {
        return edge;
    }

}
