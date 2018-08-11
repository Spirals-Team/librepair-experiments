package net.coljate.list.primitive;

import java.util.Objects;

import javax.annotation.Nonnull;

/**
 *
 * @author Ollie
 */
public class ImmutableGuavaDoubleArray implements ImmutableDoubleArray {

    private final com.google.common.primitives.ImmutableDoubleArray array;

    public ImmutableGuavaDoubleArray(@Nonnull final com.google.common.primitives.ImmutableDoubleArray array) {
        this.array = Objects.requireNonNull(array, "array");
    }

    @Override
    public ImmutableDoubleListIterator iterator() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public double getDouble(final int index) {
        return array.get(index);
    }

    @Override
    public int length() {
        return array.length();
    }

}
