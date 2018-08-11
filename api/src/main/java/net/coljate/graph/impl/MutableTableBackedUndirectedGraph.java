package net.coljate.graph.impl;

import java.util.Objects;

import net.coljate.graph.MutableUndirectedGraph;
import net.coljate.graph.UndirectedRelationship;
import net.coljate.set.MutableSet;
import net.coljate.table.impl.MutableSymmetricTable;

/**
 *
 * @author Ollie
 */
public class MutableTableBackedUndirectedGraph<V, E>
        extends TableBackedUndirectedGraph<V, E>
        implements MutableUndirectedGraph<V, E> {

    private final MutableSet<V> vertices;
    private final MutableSymmetricTable<V, E> edges;

    protected MutableTableBackedUndirectedGraph(final MutableSet<V> vertices, final MutableSymmetricTable<V, E> edges) {
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
        return edges.add(fromVertex, toVertex, edge);
    }

    @Override
    public boolean removeVertex(final Object vertex) {
        final boolean removed = vertices.remove(vertex);
        edges.removeWhere(cell -> Objects.equals(vertex, cell.rowKey()) || Objects.equals(vertex, cell.columnKey()));
        return removed;
    }

    @Override
    public boolean remove(final UndirectedRelationship<?, ?> relationship) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void clear() {
        edges.clear();
    }

}
