package net.coljate.graph.impl;

import java.util.Iterator;

import net.coljate.graph.AbstractGraph;
import net.coljate.set.Set;
import net.coljate.table.Cell;
import net.coljate.table.Table;

/**
 *
 * @author Ollie
 */
public abstract class TableBackedGraph<V, E>
        extends AbstractGraph<V, E> {

    private final Set<V> vertices;
    private final Table<V, V, E> edges;

    protected TableBackedGraph(final Set<V> vertices, final Table<V, V, E> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    @Override
    public Set<V> vertices() {
        return vertices;
    }

    protected Table<V, V, E> edgeTable() {
        return edges;
    }

    @Override
    public E edgeBetween(final Object fromVertex, final Object toVertex) {
        return edges.getIfPresent(fromVertex, toVertex);
    }

    protected Iterator<Cell<V, V, E>> edgeIterator() {
        return edges.iterator();
    }

}
