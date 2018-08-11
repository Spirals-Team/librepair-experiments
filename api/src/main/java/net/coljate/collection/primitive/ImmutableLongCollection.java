package net.coljate.collection.primitive;

import net.coljate.collection.ImmutableCollection;
import net.coljate.util.iterator.UnmodifiableIterator;

/**
 *
 * @author Ollie
 */
public interface ImmutableLongCollection extends LongCollection, ImmutableCollection<Long> {

    @Override
    @Deprecated
    default ImmutableLongCollection immutableCopy() {
        return this;
    }

    @Override
    UnmodifiableLongIterator iterator();

    interface UnmodifiableLongIterator extends LongIterator, UnmodifiableIterator<Long> {

    }

}
