package net.coljate.graph.impl;

import net.coljate.graph.DirectedRelationship;
import net.coljate.graph.ImmutableDirectedGraph;
import net.coljate.graph.Relationship;
import net.coljate.set.ImmutableSet;
import net.coljate.table.ImmutableTable;
import net.coljate.util.iterator.UnmodifiableCovariantIterator;

/**
 *
 * @author Ollie
 */
public class ImmutableTableBackedDirectedGraph<V, E>
        extends TableBackedDirectedGraph<V, E>
        implements ImmutableDirectedGraph<V, E> {

    protected ImmutableTableBackedDirectedGraph(final ImmutableSet<V> vertices, final ImmutableTable<V, V, E> table) {
        super(vertices, table);
    }

    @Override
    public ImmutableSet<V> vertices() {
        return super.vertices().immutableCopy();
    }

    @Override
    public UnmodifiableCovariantIterator<Relationship<V, E>, ? extends DirectedRelationship<V, E>> iterator() {
        return UnmodifiableCovariantIterator.wrap(super.iterator());
    }

}
