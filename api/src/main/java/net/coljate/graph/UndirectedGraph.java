package net.coljate.graph;

import net.coljate.util.iterator.CovariantIterator;

/**
 *
 * @author Ollie
 */
public interface UndirectedGraph<V, E> extends Graph<V, E> {

    @Override
    CovariantIterator<Relationship<V, E>, ? extends UndirectedRelationship<V, E>> iterator();

    @Override
    default UndirectedRelationship<V, E> relationshipBetween(final Object fromVertex, final Object toVertex) {
        return this.iterator().first(relationship -> relationship.isBetween(fromVertex, toVertex));
    }

    @Override
    MutableUndirectedGraph<V, E> mutableCopy();

    @Override
    ImmutableUndirectedGraph<V, E> immutableCopy();

}
