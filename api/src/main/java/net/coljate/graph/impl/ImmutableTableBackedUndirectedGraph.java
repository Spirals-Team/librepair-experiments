package net.coljate.graph.impl;

import net.coljate.graph.ImmutableUndirectedGraph;
import net.coljate.graph.Relationship;
import net.coljate.graph.UndirectedRelationship;
import net.coljate.graph.impl.TableBackedUndirectedGraph;
import net.coljate.set.ImmutableSet;
import net.coljate.table.impl.ImmutableSymmetricTable;
import net.coljate.util.iterator.UnmodifiableCovariantIterator;

/**
 *
 * @author Ollie
 */
public class ImmutableTableBackedUndirectedGraph<V, E>
        extends TableBackedUndirectedGraph<V, E>
        implements ImmutableUndirectedGraph<V, E> {

    protected ImmutableTableBackedUndirectedGraph(final ImmutableSet<V> vertices, final ImmutableSymmetricTable<V, E> edges) {
        super(vertices, edges);
    }

    @Override
    public ImmutableSet<V> vertices() {
        return super.vertices().immutableCopy();
    }

    @Override
    public UnmodifiableCovariantIterator<Relationship<V, E>, ? extends UndirectedRelationship<V, E>> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
