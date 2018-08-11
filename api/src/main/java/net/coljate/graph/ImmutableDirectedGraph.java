package net.coljate.graph;

import net.coljate.util.iterator.UnmodifiableCovariantIterator;

/**
 *
 * @author Ollie
 */
public interface ImmutableDirectedGraph<V, E> extends DirectedGraph<V, E>, ImmutableGraph<V, E> {

    @Override
    UnmodifiableCovariantIterator<Relationship<V, E>, ? extends DirectedRelationship<V, E>> iterator();

    @Override
    @Deprecated
    default ImmutableDirectedGraph<V, E> immutableCopy() {
        return this;
    }

}
