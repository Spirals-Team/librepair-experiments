package net.coljate.graph;

import net.coljate.graph.impl.EmptyDirectedGraph;
import net.coljate.graph.impl.SingleEdgeDirectedGraph;
import net.coljate.util.iterator.CovariantIterator;

/**
 *
 * @author Ollie
 * @see MutableDirectedGraph
 * @see ImmutableDirectedGraph
 */
public interface DirectedGraph<V, E> extends Graph<V, E> {

    @Override
    CovariantIterator<Relationship<V, E>, ? extends DirectedRelationship<V, E>> iterator();

    @Override
    default DirectedRelationship<V, E> relationshipBetween(final Object fromVertex, final Object toVertex) {
        return this.iterator().first(relationship -> relationship.isBetween(fromVertex, toVertex));
    }

    @Override
    default MutableDirectedGraph<V, E> mutableCopy() {
        return MutableDirectedGraph.copyOf(this);
    }

    @Override
    ImmutableDirectedGraph<V, E> immutableCopy();

    static <V, E> DirectedGraph<V, E> of() {
        return EmptyDirectedGraph.instance();
    }

    static <V, E> DirectedGraph<V, E> of(final V from, final E edge, final V to) {
        return new SingleEdgeDirectedGraph<>(from, to, edge);
    }

    static <V, E> MutableDirectedGraph<V, E> createHashBackedGraph() {
        return MutableDirectedGraph.createHashBackedGraph();
    }

}
