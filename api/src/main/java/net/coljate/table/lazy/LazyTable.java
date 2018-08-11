package net.coljate.table.lazy;

import net.coljate.set.lazy.LazySet;
import net.coljate.table.Cell;
import net.coljate.table.ImmutableTable;
import net.coljate.table.MutableTable;
import net.coljate.table.Table;

/**
 *
 * @author Ollie
 */
public interface LazyTable<R, C, V> extends Table<R, C, V>, LazySet<Cell<R, C, V>> {

    @Override
    MutableTable<R, C, V> mutableCopy();

    @Override
    ImmutableTable<R, C, V> immutableCopy();

}
