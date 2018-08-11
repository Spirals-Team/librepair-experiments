package net.coljate.counter.primitive;

import net.coljate.collection.primitive.LongCollection;
import net.coljate.counter.Counter;
import net.coljate.set.primitive.LongSet;

/**
 *
 * @author Ollie
 */
public interface LongCounter extends LongCollection, Counter<Long> {

    long count(long value);

    @Override
    default boolean contains(final long l) {
        return this.count(l) > 0;
    }

    @Override
    @Deprecated
    default int count(final Object element) {
        return element instanceof Long || element instanceof Integer
                ? Math.toIntExact(this.count(((Number) element).longValue()))
                : 0;
    }

    @Override
    @Deprecated
    default boolean contains(Object element) {
        return Counter.super.contains(element);
    }

    @Override
    LongSet elements();

    @Override
    MutableLongCounter mutableCopy();

    @Override
    ImmutableLongCounter immutableCopy();

}
