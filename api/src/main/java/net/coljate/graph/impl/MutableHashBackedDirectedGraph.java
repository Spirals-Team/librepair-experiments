package net.coljate.graph.impl;

import java.util.Objects;

import net.coljate.graph.DirectedGraph;
import net.coljate.graph.DirectedRelationship;
import net.coljate.graph.MutableDirectedGraph;
import net.coljate.set.MutableSet;
import net.coljate.table.MutableTable;

/**
 *
 * @author Ollie
 */
public class MutableHashBackedDirectedGraph<V, E>
        extends TableBackedDirectedGraph<V, E>
        implements MutableDirectedGraph<V, E> {

    public static <V, E> MutableHashBackedDirectedGraph<V, E> create() {
        return new MutableHashBackedDirectedGraph<>(MutableSet.createHashSet(), MutableTable.createHashMapBackedTable());
    }

    public static <V, E> MutableHashBackedDirectedGraph<V, E> copyOf(final DirectedGraph<V, E> graph) {
        final MutableHashBackedDirectedGraph<V, E> copy = create();
        graph.forEach(copy::add);
        return copy;
    }

    private final MutableSet<V> vertices;
    private final MutableTable<V, V, E> edges;

    protected MutableHashBackedDirectedGraph(final MutableSet<V> vertices, final MutableTable<V, V, E> edges) {
        super(vertices, edges);
        this.vertices = vertices;
        this.edges = edges;
    }

    @Override
    public boolean addVertex(final V vertex) {
        return vertices.add(vertex);
    }

    @Override
    public boolean addEdge(final V fromVertex, final V toVertex, final E edge) {
        this.addVertex(fromVertex);
        this.addVertex(toVertex);
        return edges.add(fromVertex, toVertex, edge);
    }

    @Override
    public boolean removeVertex(final Object vertex) {
        final boolean removed = vertices.remove(vertex);
        edges.removeWhere(cell -> Objects.equals(cell.rowKey(), vertex) || Objects.equals(cell.columnKey(), vertex));
        return removed;
    }

    @Override
    public boolean remove(final DirectedRelationship<?, ?> relationship) {
        return edges.remove(relationship.from(), relationship.to(), relationship.edge());
    }

    @Override
    public void clear() {
        edges.clear();
    }

}
