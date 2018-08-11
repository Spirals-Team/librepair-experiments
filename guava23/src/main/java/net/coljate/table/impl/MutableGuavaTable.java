package net.coljate.table.impl;

import net.coljate.table.MutableTable;

/**
 *
 * @author Ollie
 */
public class MutableGuavaTable<R, C, V>
        extends GuavaTable<R, C, V>
        implements MutableTable<R, C, V> {

    public static <R, C, V> MutableGuavaTable<R, C, V> copyIntoHashTable(final com.google.common.collect.Table<R, C, V> table) {
        return new MutableGuavaTable<>(com.google.common.collect.HashBasedTable.create(table));
    }

    protected MutableGuavaTable(final com.google.common.collect.Table<R, C, V> table) {
        super(table);
    }

    @Override
    public V put(final R rowKey, C columnKey, V value) {
        return table.put(rowKey, columnKey, value);
    }

    @Override
    public V evict(final Object rowKey, final Object columnKey) {
        return table.remove(rowKey, columnKey);
    }

    @Override
    public void clear() {
        table.clear();
    }

}
