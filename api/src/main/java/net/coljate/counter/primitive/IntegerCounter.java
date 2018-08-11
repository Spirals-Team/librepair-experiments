package net.coljate.counter.primitive;

import net.coljate.collection.primitive.IntegerCollection;
import net.coljate.counter.Counter;
import net.coljate.set.primitive.IntegerSet;

/**
 *
 * @author ollie
 */
public interface IntegerCounter extends IntegerCollection, Counter<Long> {

    int count(int value);

    @Override
    default boolean contains(int i) {
        return this.count(i) > 0;
    }

    @Override
    @Deprecated
    default int count(final Object element) {
        return element instanceof Integer
                ? this.count(((Integer) element).intValue())
                : 0;
    }

    @Override
    @Deprecated
    default boolean contains(Object element) {
        return Counter.super.contains(element);
    }

    @Override
    IntegerSet elements();

}
