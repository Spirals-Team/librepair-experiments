package net.coljate.graph;

import java.util.Objects;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import net.coljate.collection.Collection;
import net.coljate.map.Map;
import net.coljate.map.MutableMap;
import net.coljate.set.MutableSet;
import net.coljate.set.Set;
import net.coljate.util.functions.Functions;

/**
 * A data structure where pairs of objects are "related" somehow.
 *
 * We consider graphs to be collections of relationships between vertices. As such as their {@link #count} is the number
 * of relationships, and a graph with zero relationships but non-zero vertices is still considered to be
 * {@link #isEmpty() empty}.
 *
 * @author Ollie
 * @param <V> vertex type
 * @param <E> edge type
 * @since 1.0
 */
public interface Graph<V, E> extends Set<Relationship<V, E>> {

    default Set<V> vertices() {
        final MutableSet<V> vertices = MutableSet.createHashSet();
        this.forEach(relationship -> vertices.addAll(relationship.vertices()));
        return vertices;
    }

    /**
     * @return the number of vertices.
     */
    default int order() {
        return this.vertices().count();
    }

    @Nonnull
    default Collection<E> edges() {
        return this.transform(Relationship::edge);
    }

    /**
     * @return the number of edges.
     */
    default int size() {
        return this.edges().count();
    }

    @CheckForNull
    default Relationship<V, E> relationshipBetween(final Object fromVertex, final Object toVertex) {
        return this.first(relationship -> relationship.isBetween(fromVertex, toVertex));
    }

    @Nonnull
    default Map<V, E> neighbours(final V vertex) {
        final MutableMap<V, E> neighbours = MutableMap.createHashMap();
        final Set<Relationship<V, E>> relevant = this.filter(relationship -> relationship.containsVertex(vertex));
        relevant.forEach(relationship -> neighbours.put(relationship.otherVertex(vertex).get(), relationship.edge()));
        return neighbours;
    }

    @CheckForNull
    default E edgeBetween(final Object fromVertex, final Object toVertex) {
        return Functions.ifNonNull(this.relationshipBetween(fromVertex, toVertex), Relationship::edge);
    }

    default int degree(final V vertex) {
        return this.neighbours(vertex).count();
    }

    @Deprecated
    default boolean contains(final Object object) {
        return object instanceof Relationship
                && this.contains((Relationship) object);
    }

    default boolean contains(final Relationship<?, ?> relationship) {
        return this.anyMatch(r -> Objects.equals(r, relationship));
    }

    default boolean containsVertex(final Object vertex) {
        return this.vertices().contains(vertex);
    }

    /**
     * @return the count of relationships.
     */
    @Override
    default int count() {
        return Set.super.count();
    }

    @Override
    MutableGraph<V, E> mutableCopy();

    @Override
    ImmutableGraph<V, E> immutableCopy();

    static <V, E> Graph<V, E> createEmptyDirectedGraph() {
        return DirectedGraph.of();
    }

    static <V, E> MutableDirectedGraph<V, E> createHashBackedDirectedGraph() {
        return MutableDirectedGraph.createHashBackedGraph();
    }

    static <V, E> Graph<V, E> of(final V vertex) {
        return ImmutableGraph.of(vertex);
    }

    static <V, E> Graph<V, E> of(final V v1, final E edge, final V v2) {
        return DirectedGraph.of(v1, edge, v2);
    }

}
