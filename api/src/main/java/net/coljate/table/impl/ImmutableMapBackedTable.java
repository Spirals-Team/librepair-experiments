package net.coljate.table.impl;

import net.coljate.map.Entry;
import net.coljate.map.ImmutableMap;
import net.coljate.table.Cell;
import net.coljate.table.ImmutableCell;
import net.coljate.table.ImmutableTable;
import net.coljate.table.Table;
import net.coljate.util.iterator.UnmodifiableIterator;

/**
 *
 * @author Ollie
 */
public class ImmutableMapBackedTable<R, C, V>
        extends MapBackedTable<R, C, V>
        implements ImmutableTable<R, C, V> {

    public static <R, C, V> ImmutableMapBackedTable<R, C, V> copyOf(final Table<? extends R, ? extends C, ? extends V> that) {
        return MutableMapBackedTable.<R, C, V>copyOf(that).immutableCopy();
    }

    protected ImmutableMapBackedTable(final ImmutableMap<KeyPair<R, C>, V> map) {
        super(map);
    }

    @Override
    public ImmutableCell<R, C, V> cellIfPresent(final Object row, final Object column) {
        return this.toCell(this.entry(row, column));
    }

    @Override
    protected ImmutableCell<R, C, V> toCell(final Entry<KeyPair<R, C>, V> entry) {
        return entry == null
                ? null
                : new ImmutableCell<>(entry.key().rowKey(), entry.key().columnKey(), entry.value());
    }

    @Override
    public UnmodifiableIterator<Cell<R, C, V>> iterator() {
        return UnmodifiableIterator.wrap(super.iterator());
    }

}
