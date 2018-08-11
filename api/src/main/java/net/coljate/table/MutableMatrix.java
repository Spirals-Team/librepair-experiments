package net.coljate.table;

import java.util.Objects;

import net.coljate.table.impl.MutableArrayBackedMatrix;

/**
 *
 * @author Ollie
 */
public interface MutableMatrix<T>
        extends Matrix<T>, MutableTable<Integer, Integer, T> {

    T put(int x, int y, T value);

    @Override
    default T put(final Integer x, final Integer y, final T value) {
        return this.put(x.intValue(), y.intValue(), value);
    }

    T evict(int x, int y);

    @Deprecated
    default T evict(final Object x, final Object y) {
        return x instanceof Integer && y instanceof Integer
                ? this.evict(((Integer) x).intValue(), ((Integer) y).intValue())
                : null;
    }

    default boolean remove(final int x, final int y, final T value) {
        final T current = this.get(x, y);
        if (Objects.equals(current, value)) {
            this.evict(x, y);
            return true;
        } else {
            return false;
        }
    }

    @Override
    default boolean remove(final Object x, final Object y) {
        return x instanceof Integer
                && y instanceof Integer
                && this.evict((int) x, (int) y) != null;
    }

    @Override
    default boolean remove(final Object x, final Object y, final Object value) {
        if (x instanceof Integer && y instanceof Integer) {
            final T current = this.get((int) x, (int) y);
            return Objects.equals(current, value)
                    ? this.evict((int) x, (int) y) == current
                    : false;
        } else {
            return false;
        }
    }

    static <T> MutableMatrix<T> create(final int rows, final int columns) {
        return MutableArrayBackedMatrix.create(rows, columns);
    }

}
