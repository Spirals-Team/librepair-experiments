package net.coljate.graph.impl;

import net.coljate.graph.AbstractUndirectedRelationship;
import net.coljate.graph.ImmutableUndirectedGraph;
import net.coljate.graph.Relationship;
import net.coljate.graph.UndirectedGraph;
import net.coljate.graph.UndirectedRelationship;
import net.coljate.set.Set;
import net.coljate.set.impl.TwoSet;
import net.coljate.table.Cell;
import net.coljate.table.impl.SymmetricTable;
import net.coljate.util.iterator.CovariantIterator;
import net.coljate.util.iterator.Iterators;

/**
 *
 * @author Ollie
 */
public class TableBackedUndirectedGraph<V, E>
        extends TableBackedGraph<V, E>
        implements UndirectedGraph<V, E> {

    private final SymmetricTable<V, E> edges;

    protected TableBackedUndirectedGraph(final Set<V> vertices, final SymmetricTable<V, E> edges) {
        super(vertices, edges);
        this.edges = edges;
    }

    @Override
    protected SymmetricTable<V, E> edgeTable() {
        return edges;
    }

    @Override
    public CovariantIterator<Relationship<V, E>, ? extends UndirectedRelationship<V, E>> iterator() {
        return Iterators.transform(this.edgeIterator(), this::toRelationship);
    }

    private UndirectedRelationship<V, E> toRelationship(final Cell<V, V, E> cell) {
        return new CellBackedUndirectedRelationship(cell);
    }

    @Override
    public MutableTableBackedUndirectedGraph<V, E> mutableCopy() {
        return new MutableTableBackedUndirectedGraph<>(this.vertices().mutableCopy(), edges.mutableCopy());
    }

    @Override
    public ImmutableUndirectedGraph<V, E> immutableCopy() {
        return new ImmutableTableBackedUndirectedGraph<>(this.vertices().immutableCopy(), edges.immutableCopy());
    }

    private final class CellBackedUndirectedRelationship
            extends AbstractUndirectedRelationship<V, E> {

        private final Cell<V, V, E> cell;

        CellBackedUndirectedRelationship(final Cell<V, V, E> cell) {
            this.cell = cell;
        }

        @Override
        public E edge() {
            return cell.value();
        }

        @Override
        public TwoSet<V> vertices() {
            return TwoSet.require(cell.rowKey(), cell.columnKey());
        }

        @Override
        public boolean containsVertex(final Object vertex) {
            return cell.containsKey(vertex);
        }

    }

}
