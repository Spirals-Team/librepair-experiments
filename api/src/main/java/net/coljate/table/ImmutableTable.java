package net.coljate.table;

import net.coljate.set.ImmutableSet;
import net.coljate.table.impl.ImmutableMapBackedTable;

/**
 *
 * @author Ollie
 */
public interface ImmutableTable<R, C, V>
        extends Table<R, C, V>, ImmutableSet<Cell<R, C, V>> {

    @Override
    ImmutableCell<R, C, V> cellIfPresent(Object row, Object column);

    @Override
    default ImmutableCell<R, C, V> cell(final R row, final C column) {
        return this.cellIfPresent(row, column);
    }

    @Override
    @Deprecated
    default ImmutableTable<R, C, V> immutableCopy() {
        return this;
    }

    static <R, C, V> ImmutableTable<R, C, V> copyOf(final Table<? extends R, ? extends C, ? extends V> table) {
        return ImmutableMapBackedTable.copyOf(table);
    }

}
