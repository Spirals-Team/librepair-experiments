package net.coljate.list.primitive;

import net.coljate.list.Array;

/**
 *
 * @author Ollie
 */
public interface DoubleArray extends DoubleList, Array<Double> {

    /**
     *
     * @param index
     * @return
     * @throws ArrayIndexOutOfBoundsException
     */
    double getDouble(int index);

    @Override
    @Deprecated
    default Double get(final int index) {
        return this.getDouble(index);
    }

    @Override
    @Deprecated
    default boolean contains(final Object object) {
        return DoubleList.super.contains(object);
    }

    @Override
    default DoubleListIterator iterator() {
        return new DoubleArrayIterator(this);
    }
    
    @Override
    default DoubleListIterator reverseIterator() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    default double[] doubleArrayCopy() {
        final int length = this.length();
        final double[] array = new double[length];
        for (int i = 0; i < length; i++) {
            array[i] = this.getDouble(i);
        }
        return array;
    }

    @Override
    default MutableDoubleArray mutableCopy() {
        return MutableNativeDoubleArray.copyOf(this);
    }

    @Override
    default ImmutableDoubleArray immutableCopy() {
        return ImmutableNativeDoubleArray.copyOf(this);
    }

    class DoubleArrayIterator implements DoubleList.DoubleListIterator {

        private final DoubleArray array;
        private int index;

        public DoubleArrayIterator(final DoubleArray array) {
            this.array = array;
        }

        @Override
        public double previousDouble() {
            return array.getDouble(--index);
        }

        @Override
        public double nextDouble() {
            return array.getDouble(index++);
        }

        @Override
        public boolean hasNext() {
            return index < array.length();
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

    }

}
