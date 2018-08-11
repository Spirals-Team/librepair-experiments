package net.coljate.table.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import net.coljate.collection.Collection;
import net.coljate.map.Entry;
import net.coljate.map.Map;
import net.coljate.table.AbstractTable;
import net.coljate.table.Cell;
import net.coljate.util.Hashing;
import net.coljate.util.functions.Functions;

/**
 * A table whose entry {@code (K1, K2)} is always identical to {@code (K2, K1)}.
 *
 * @author Ollie
 */
public class SymmetricTable<K, V> extends AbstractTable<K, K, V> {

    public static <K, V> SymmetricTable<K, V> of(final Cell<? extends K, ? extends K, ? extends V> cell) {
        return of(cell.rowKey(), cell.columnKey(), cell.value());
    }

    public static <K, V> SymmetricTable<K, V> of(final K row, final K column, final V value) {
        final Map<OneOrTwoSet<K>, V> map = Map.of(new OneOrTwoSet<>(row, column), value);
        return new SymmetricTable<>(map);
    }

    private final Map<OneOrTwoSet<K>, V> map;

    protected SymmetricTable(final Map<OneOrTwoSet<K>, V> map) {
        this.map = map;
    }

    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Cell<K, K, V> cellIfPresent(final Object row, final Object column) {
        final OneOrTwoSet<?> set = new OneOrTwoSet<>(row, column);
        return Functions.ifNonNull(map.getEntry(set), this::toCell);
    }

    private Cell<K, K, V> toCell(final Entry<OneOrTwoSet<K>, V> entry) {
        return this.toCell(entry, false);
    }

    private Cell<K, K, V> toCell(final Entry<OneOrTwoSet<K>, V> entry, final boolean reverse) {
        final OneOrTwoSet<K> set = entry.key();
        final K row = reverse ? set.second() : set.first();
        final K column = reverse ? set.first() : set.second();
        final V value = entry.value();
        return Cell.of(row, column, value);
    }

    @Override
    public Iterator<Cell<K, K, V>> iterator() {
        return new SymmetricIterator(map.iterator());
    }

    @Override
    public MutableSymmetricTable<K, V> mutableCopy() {
        return new MutableSymmetricTable<>(map.mutableCopy());
    }

    @Override
    public ImmutableSymmetricTable<K, V> immutableCopy() {
        return new ImmutableSymmetricTable<>(map.immutableCopy());
    }

    protected static final class OneOrTwoSet<K> {

        private final K first, second;

        OneOrTwoSet(final K first, final K second) {
            this.first = first;
            this.second = second;
        }

        K first() {
            return first;
        }

        K second() {
            return second;
        }

        int count() {
            return Objects.equals(first, second) ? 1 : 2;
        }

        @Override
        public boolean equals(final Object that) {
            return that instanceof OneOrTwoSet
                    && this.equals((OneOrTwoSet) that);
        }

        boolean equals(final OneOrTwoSet<?> that) {
            return Objects.equals(this.first, that.first)
                    ? Objects.equals(this.second, that.second)
                    : Objects.equals(this.first, that.second) && Objects.equals(this.second, that.first);
        }

        @Override
        public int hashCode() {
            return Hashing.unorderedHash(first, second);
        }

    }

    protected class SymmetricIterator implements Iterator<Cell<K, K, V>> {

        private final Iterator<Entry<OneOrTwoSet<K>, V>> iterator;
        private Entry<OneOrTwoSet<K>, V> current;
        private boolean doFirst = true;

        SymmetricIterator(final Iterator<Entry<OneOrTwoSet<K>, V>> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext() || (current != null && !doFirst);
        }

        @Override
        public Cell<K, K, V> next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            if (doFirst) {
                current = iterator.next();
                //Reset iff the keys are different
                doFirst = current.key().count() == 1;
                return toCell(current, true);
            } else {
                doFirst = true;
                return toCell(current, false);
            }
        }

    }

}
