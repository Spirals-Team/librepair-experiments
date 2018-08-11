package net.coljate.table;

import java.util.Objects;

/**
 *
 * @author Ollie
 */
public abstract class AbstractCell<R, C, V> implements Cell<R, C, V> {

    @Override
    public boolean equals(final Object object) {
        return object instanceof Cell
                && Cell.equals(this, (Cell<?, ?, ?>) object);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.rowKey());
        hash = 19 * hash + Objects.hashCode(this.columnKey());
        hash = 19 * hash + Objects.hashCode(this.value());
        return hash;
    }

    @Override
    public String toString() {
        return Cell.toString(this);
    }

}
