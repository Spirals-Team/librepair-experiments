package net.coljate.collection.primitive;

import net.coljate.collection.Collection;

import java.util.function.IntBinaryOperator;

/**
 * @author Ollie
 */
public interface IntegerCollection extends IntegerContainer, Collection<Long>, LongIterable {

    @Override
    default boolean contains(final int i) {
        return this.contains((long) i);
    }

    @Override
    default boolean contains(final long l) {
        for (final IntegerIterator iterator = this.iterator(); iterator.hasNext(); ) {
            if (iterator.nextLong() == l) {
                return true;
            }
        }
        return false;
    }

    @Override
    default boolean contains(final Object object) {
        return IntegerContainer.super.contains(object);
    }

    @Override
    IntegerIterator iterator();

    default int reduce(final IntBinaryOperator operator, final int initialValue) {
        int current = initialValue;
        for (final IntegerIterator iterator = this.iterator(); iterator.hasNext(); ) {
            current = operator.applyAsInt(current, iterator.nextInt());
        }
        return current;
    }

    default int intSum() throws ArithmeticException {
        return this.reduce((final int i1, final int i2) -> Math.addExact(i1, i2), 0);
    }

    default int intProduct() throws ArithmeticException {
        return this.reduce((final int i1, final int i2) -> Math.multiplyExact(i1, i2), 1);
    }

    interface IntegerIterator extends LongIterator {

        int nextInt();

        @Override
        default long nextLong() {
            return this.nextInt();
        }

    }

}
