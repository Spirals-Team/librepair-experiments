package net.coljate.graph.impl;

import net.coljate.graph.DirectedRelationship;
import net.coljate.graph.ImmutableDirectedGraph;
import net.coljate.graph.Relationship;
import net.coljate.util.iterator.Iterators;
import net.coljate.util.iterator.UnmodifiableCovariantIterator;

/**
 *
 * @author ollie
 */
public class SingleEdgeDirectedGraph<V, E>
        extends SingleEdgeGraph<V, E>
        implements ImmutableDirectedGraph<V, E> {

    public SingleEdgeDirectedGraph(final V from, final V to, final E edge) {
        super(from, to, edge);
    }

    protected DirectedRelationship<V, E> directedRelationship() {
        return new RegularDirectedRelationship<>(this.from(), this.to(), this.edge());
    }

    @Override
    public UnmodifiableCovariantIterator<Relationship<V, E>, ? extends DirectedRelationship<V, E>> iterator() {
        return UnmodifiableCovariantIterator.wrap(Iterators.of(this.directedRelationship()));
    }

}
