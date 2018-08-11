package net.coljate.table;

/**
 *
 * @author Ollie
 */
public class ImmutableCell<R, C, V> extends AbstractCell<R, C, V> {

    private final R rowKey;
    private final C columnKey;
    private final V value;

    public ImmutableCell(final R rowKey, final C columnKey, final V value) {
        this.rowKey = rowKey;
        this.columnKey = columnKey;
        this.value = value;
    }

    @Override
    public R rowKey() {
        return rowKey;
    }

    @Override
    public C columnKey() {
        return columnKey;
    }

    @Override
    public V value() {
        return value;
    }

    @Override
    @Deprecated
    public ImmutableCell<R, C, V> immutableCopy() {
        return this;
    }

}
