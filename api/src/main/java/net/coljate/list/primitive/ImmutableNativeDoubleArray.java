package net.coljate.list.primitive;

import javax.annotation.Nonnull;

import net.coljate.collection.primitive.DoubleCollection;

/**
 *
 * @author Ollie
 */
public class ImmutableNativeDoubleArray extends NativeDoubleArray implements ImmutableDoubleArray {

    public static ImmutableDoubleArray copyOf(@Nonnull final DoubleCollection collection) {
        return new ImmutableNativeDoubleArray(DoubleCollection.toArray(collection));
    }

    protected ImmutableNativeDoubleArray(final double[] array) {
        super(array);
    }

    @Override
    public ImmutableDoubleListIterator iterator() {
        return ImmutableDoubleArray.super.iterator();
    }

    @Override
    @Deprecated
    public ImmutableNativeDoubleArray immutableCopy() {
        return this;
    }

}
