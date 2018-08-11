package net.coljate.graph.impl;

import net.coljate.graph.DirectedRelationship;
import net.coljate.graph.ImmutableDirectedGraph;
import net.coljate.graph.Relationship;
import net.coljate.util.iterator.UnmodifiableCovariantIterator;

/**
 *
 * @author Ollie
 */
public class EmptyDirectedGraph<V, E>
        extends EmptyGraph<V, E>
        implements ImmutableDirectedGraph<V, E> {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("rawtypes")
    private static final EmptyDirectedGraph INSTANCE = new EmptyDirectedGraph();

    @SuppressWarnings("unchecked")
    public static <V, E> EmptyDirectedGraph<V, E> instance() {
        return INSTANCE;
    }

    @Override
    public UnmodifiableCovariantIterator<Relationship<V, E>, ? extends DirectedRelationship<V, E>> iterator() {
        return UnmodifiableCovariantIterator.of();
    }

    @Override
    @Deprecated
    public EmptyDirectedGraph<V, E> immutableCopy() {
        return this;
    }

}
