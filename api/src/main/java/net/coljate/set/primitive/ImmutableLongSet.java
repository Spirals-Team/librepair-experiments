package net.coljate.set.primitive;

import net.coljate.collection.primitive.ImmutableLongCollection;
import net.coljate.set.ImmutableSet;

/**
 *
 * @author Ollie
 */
public interface ImmutableLongSet extends LongSet, ImmutableLongCollection, ImmutableSet<Long> {

    @Override
    @Deprecated
    default ImmutableLongSet immutableCopy() {
        return this;
    }

}
