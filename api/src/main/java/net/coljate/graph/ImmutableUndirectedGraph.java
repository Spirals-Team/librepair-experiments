package net.coljate.graph;

import net.coljate.util.iterator.UnmodifiableCovariantIterator;

/**
 *
 * @author Ollie
 */
public interface ImmutableUndirectedGraph<V, E>
        extends ImmutableGraph<V, E>, UndirectedGraph<V, E> {

    @Override
    UnmodifiableCovariantIterator<Relationship<V, E>, ? extends UndirectedRelationship<V, E>> iterator();

    @Override
    @Deprecated
    default ImmutableUndirectedGraph<V, E> immutableCopy() {
        return this;
    }

}
