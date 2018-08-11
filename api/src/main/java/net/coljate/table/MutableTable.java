package net.coljate.table;

import java.util.Objects;
import java.util.function.BiFunction;

import net.coljate.set.MutableSet;
import net.coljate.table.impl.MutableMapBackedTable;
import net.coljate.table.impl.MutableSymmetricTable;

/**
 *
 * @author Ollie
 */
public interface MutableTable<R, C, V>
        extends Table<R, C, V>, MutableSet<Cell<R, C, V>> {

    V put(R rowKey, C columnKey, V value);

    default V computeIfAbsent(final R rowKey, final C columnKey, final BiFunction<? super R, ? super C, ? extends V> valueFunction) {
        Cell<R, C, V> cell = this.cell(rowKey, columnKey);
        if (cell == null) {
            final V value = valueFunction.apply(rowKey, columnKey);
            this.put(rowKey, columnKey, value);
            return value;
        } else {
            return cell.value();
        }
    }

    @Override
    default boolean add(final Cell<R, C, V> cell) {
        return this.addCell(cell);
    }

    default boolean addCell(final Cell<? extends R, ? extends C, ? extends V> cell) {
        return this.add(cell.rowKey(), cell.columnKey(), cell.value());
    }

    default boolean add(final R rowKey, final C columnKey, final V value) {
        return Objects.equals(this.get(rowKey, columnKey), value) && this.contains(rowKey, columnKey)
                ? false
                : this.put(rowKey, columnKey, value) == null;
    }

    default boolean remove(Object rowKey, Object columnKey, Object value) {
        final V current = this.getIfPresent(rowKey, columnKey);
        return Objects.equals(current, value)
                ? Objects.equals(this.evict(rowKey, columnKey), value)
                : false;
    }

    V evict(Object rowKey, Object columnKey);

    @Override
    @Deprecated
    default boolean remove(final Object object) {
        return object instanceof Cell
                && this.remove((Cell) object);
    }

    default boolean remove(final Cell<?, ?, ?> cell) {
        return this.remove(cell.rowKey(), cell.columnKey(), cell.value());
    }

    default boolean remove(final Object rowKey, final Object columnKey) {
        final Cell<R, C, V> cell = this.cellIfPresent(rowKey, columnKey);
        return this.remove(cell);
    }

    static <R, C, V> MutableTable<R, C, V> copyOf(final Table<? extends R, ? extends C, ? extends V> table) {
        return MutableMapBackedTable.copyOf(table);
    }

    static <R, C, V> MutableTable<R, C, V> createHashMapBackedTable() {
        return MutableMapBackedTable.createBackedByHashMap();
    }

    static <K, V> MutableSymmetricTable<K, V> createSymmetricHashTable() {
        return MutableSymmetricTable.createHashBackedTable();
    }

}
