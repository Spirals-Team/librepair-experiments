package net.coljate.counter.primitive;

import net.coljate.collection.primitive.ImmutableLongCollection;
import net.coljate.counter.ImmutableCounter;

/**
 *
 * @author Ollie
 */
public interface ImmutableLongCounter extends LongCounter, ImmutableLongCollection, ImmutableCounter<Long> {

    @Override
    @Deprecated
    default ImmutableLongCounter immutableCopy() {
        return this;
    }

}
