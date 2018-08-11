package net.coljate.table;

/**
 *
 * @author Ollie
 */
public interface CellConsumer<R, C, V> {

    void accept(R row, C column, V value);

    default void accept(final Cell<? extends R, ? extends C, ? extends V> cell) {
        this.accept(cell.rowKey(), cell.columnKey(), cell.value());
    }

}
