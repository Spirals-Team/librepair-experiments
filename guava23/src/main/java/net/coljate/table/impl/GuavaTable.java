package net.coljate.table.impl;

import java.util.Iterator;

import net.coljate.table.AbstractCell;
import net.coljate.table.AbstractTable;
import net.coljate.table.Cell;
import net.coljate.util.iterator.Iterators;

/**
 *
 * @author Ollie
 */
public class GuavaTable<R, C, V> extends AbstractTable<R, C, V> {

    final com.google.common.collect.Table<R, C, V> table;

    protected GuavaTable(final com.google.common.collect.Table<R, C, V> table) {
        this.table = table;
    }

    @Override
    public V get(final R row, final C columnKey) {
        return table.get(row, columnKey);
    }

    @Override
    public V getIfPresent(final Object rowKey, final Object columnKey) {
        return table.get(rowKey, columnKey);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Cell<R, C, V> cellIfPresent(final Object row, final Object column) {
        return table.contains(row, column)
                ? new WrappedCell((R) row, (C) column)
                : null;
    }

    @Override
    public Iterator<Cell<R, C, V>> iterator() {
        return Iterators.transform(table.cellSet().iterator(), GuavaCell::new);
    }

    @Override
    public MutableGuavaTable<R, C, V> mutableCopy() {
        return MutableGuavaTable.copyIntoHashTable(table);
    }

    @Override
    public ImmutableGuavaTable<R, C, V> immutableCopy() {
        return ImmutableGuavaTable.copyOf(table);
    }

    protected class WrappedCell extends AbstractCell<R, C, V> {

        private final R row;
        private final C column;

        protected WrappedCell(final R row, final C column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public R rowKey() {
            return row;
        }

        @Override
        public C columnKey() {
            return column;
        }

        @Override
        public V value() {
            return get(row, column);
        }

    }

    protected static class GuavaCell<R, C, V> extends AbstractCell<R, C, V> {

        private final com.google.common.collect.Table.Cell<R, C, V> cell;

        public GuavaCell(final com.google.common.collect.Table.Cell<R, C, V> cell) {
            this.cell = cell;
        }

        @Override
        public R rowKey() {
            return cell.getRowKey();
        }

        @Override
        public C columnKey() {
            return cell.getColumnKey();
        }

        @Override
        public V value() {
            return cell.getValue();
        }

    }

}
