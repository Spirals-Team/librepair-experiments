package net.coljate.collection.primitive;

import net.coljate.collection.Collection;

/**
 *
 * @author Ollie
 */
public interface LongCollection extends Collection<Long>, LongIterable {

    @Override
    default boolean contains(Object object) {
        return LongIterable.super.contains(object);
    }

    @Override
    MutableLongCollection mutableCopy();

    @Override
    ImmutableLongCollection immutableCopy();

}
