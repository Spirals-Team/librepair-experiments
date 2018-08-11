package net.coljate.table.impl;

import net.coljate.table.Cell;
import net.coljate.table.ImmutableCell;
import net.coljate.table.ImmutableTable;
import net.coljate.util.iterator.UnmodifiableIterator;

/**
 *
 * @author Ollie
 */
public class ImmutableGuavaTable<R, C, V>
        extends GuavaTable<R, C, V>
        implements ImmutableTable<R, C, V> {

    public static <R, C, V> ImmutableGuavaTable<R, C, V> copyOf(final com.google.common.collect.Table<R, C, V> table) {
        return new ImmutableGuavaTable<>(com.google.common.collect.ImmutableTable.copyOf(table));
    }

    protected ImmutableGuavaTable(final com.google.common.collect.ImmutableTable<R, C, V> table) {
        super(table);
    }

    @Override
    public ImmutableCell<R, C, V> cellIfPresent(final Object row, final Object column) {
        return super.cellIfPresent(row, column).immutableCopy();
    }

    @Override
    public UnmodifiableIterator<Cell<R, C, V>> iterator() {
        return UnmodifiableIterator.wrap(super.iterator());
    }

    @Override
    @Deprecated
    public ImmutableGuavaTable<R, C, V> immutableCopy() {
        return this;
    }

}
