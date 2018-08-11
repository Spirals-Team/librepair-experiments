package net.coljate.table.impl;

import net.coljate.util.iterator.UnmodifiableIterator;
import net.coljate.table.Cell;
import net.coljate.table.ImmutableCell;
import net.coljate.table.ImmutableTable;

/**
 *
 * @author Ollie
 */
public class EmptyTable<R, C, V>
        implements ImmutableTable<R, C, V> {

    @Override
    public ImmutableCell<R, C, V> cellIfPresent(Object row, Object column) {
        return null;
    }

    @Override
    public boolean contains(final Object row, Object column) {
        return false;
    }

    @Override
    public UnmodifiableIterator<Cell<R, C, V>> iterator() {
        return UnmodifiableIterator.of();
    }

    @Override
    @Deprecated
    public EmptyTable<R, C, V> immutableCopy() {
        return this;
    }

}
