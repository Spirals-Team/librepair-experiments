package net.coljate.list.impl;

import net.coljate.list.AbstractArray;
import net.coljate.list.ImmutableArray;

/**
 *
 * @author Ollie
 */
public class RepeatedValueArray<T>
        extends AbstractArray<T>
        implements ImmutableArray<T> {

    public static <T> ImmutableArray<T> of(final T value, final int length) {
        return length == 0
                ? ImmutableArray.empty()
                : new RepeatedValueArray<>(value, length);
    }

    private final T value;
    private final int length;

    protected RepeatedValueArray(final T value, final int length) {
        this.value = value;
        this.length = length;
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public T get(final int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
        }
        return value;
    }

    @Override
    @Deprecated
    public RepeatedValueArray<T> immutableCopy() {
        return this;
    }

}
