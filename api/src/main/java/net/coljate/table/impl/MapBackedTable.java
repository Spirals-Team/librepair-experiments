package net.coljate.table.impl;

import java.util.Iterator;
import java.util.Objects;

import net.coljate.map.Entry;
import net.coljate.map.Map;
import net.coljate.table.AbstractCell;
import net.coljate.table.Cell;
import net.coljate.table.ImmutableTable;
import net.coljate.table.MutableTable;
import net.coljate.table.Table;
import net.coljate.util.iterator.Iterators;

/**
 *
 * @author Ollie
 */
public class MapBackedTable<R, C, V> implements Table<R, C, V> {
    
    private final Map<KeyPair<R, C>, V> map;

    protected MapBackedTable(final Map<KeyPair<R, C>, V> map) {
        this.map = map;
    }

    @Override
    public boolean contains(final Object row, final Object column) {
        return map.containsKey(new KeyPair<>(row, column));
    }

    @Override
    public Cell<R, C, V> cellIfPresent(final Object row, final Object column) {
        return this.toCell(this.entry(row, column));
    }

    protected Entry<KeyPair<R, C>, V> entry(final Object row, final Object column) {
        return map.getEntry(new KeyPair<>(row, column));
    }

    protected Cell<R, C, V> toCell(final Entry<KeyPair<R, C>, V> entry) {
        return entry == null ? null : new EntryBackedCell<>(entry);
    }

    @Override
    public Iterator<Cell<R, C, V>> iterator() {
        return Iterators.transform(map.iterator(), EntryBackedCell::new);
    }

    @Override
    public MutableTable<R, C, V> mutableCopy() {
        return new MutableMapBackedTable<>(map.mutableCopy());
    }

    @Override
    public ImmutableTable<R, C, V> immutableCopy() {
        return new ImmutableMapBackedTable<>(map.immutableCopy());
    }

    protected final class KeyPair<R, C> {

        private final R rowKey;
        private final C columnKey;

        KeyPair(final R rowKey, final C columnKey) {
            this.rowKey = rowKey;
            this.columnKey = columnKey;
        }

        R rowKey() {
            return rowKey;
        }

        C columnKey() {
            return columnKey;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 37 * hash + Objects.hashCode(this.rowKey);
            hash = 37 * hash + Objects.hashCode(this.columnKey);
            return hash;
        }

        @Override
        public boolean equals(final Object object) {
            return object instanceof KeyPair
                    && this.equals((KeyPair<?, ?>) object);
        }

        protected boolean equals(final KeyPair<?, ?> that) {
            return Objects.equals(rowKey, that.rowKey)
                    && Objects.equals(columnKey, that.columnKey);
        }

    }

    protected class EntryBackedCell<R, C, V> extends AbstractCell<R, C, V> {

        private final KeyPair<R, C> keys;
        private final V value;

        public EntryBackedCell(final Entry<KeyPair<R, C>, V> entry) {
            this(entry.key(), entry.value());
        }

        protected EntryBackedCell(final KeyPair<R, C> keys, final V value) {
            this.keys = keys;
            this.value = value;
        }

        @Override
        public R rowKey() {
            return keys.rowKey();
        }

        @Override
        public C columnKey() {
            return keys.columnKey();
        }

        @Override
        public V value() {
            return value;
        }

    }

}
