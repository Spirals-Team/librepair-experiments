package net.coljate.table;

import java.util.Objects;

/**
 *
 * @author Ollie
 * @see Table
 */
public interface Cell<R, C, V> {

    R rowKey();

    C columnKey();

    V value();

    default ImmutableCell<R, C, V> immutableCopy() {
        return new ImmutableCell<>(this.rowKey(), this.columnKey(), this.value());
    }

    default boolean containsKey(final Object key) {
        return Objects.equals(key, this.rowKey()) || Objects.equals(key, this.columnKey());
    }

    static <R, C, V> Cell<R, C, V> of(final R rowKey, final C columnKey, final V value) {
        return new ImmutableCell<>(rowKey, columnKey, value);
    }

    static String toString(final Cell<?, ?, ?> cell) {
        return cell.getClass().getSimpleName()
                + ":[" + cell.rowKey()
                + ":" + cell.columnKey()
                + ":" + cell.value()
                + "]";
    }

    static boolean equals(final Cell<?, ?, ?> c1, final Cell<?, ?, ?> c2) {
        return c1 == c2
                ? true
                : Objects.equals(c1.rowKey(), c2.rowKey())
                && Objects.equals(c1.columnKey(), c2.columnKey())
                && Objects.equals(c1.value(), c2.value());
    }

}
