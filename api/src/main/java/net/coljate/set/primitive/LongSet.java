package net.coljate.set.primitive;

import net.coljate.collection.primitive.LongCollection;
import net.coljate.set.Set;

/**
 *
 * @author Ollie
 */
public interface LongSet extends Set<Long>, LongCollection {

    @Override
    MutableLongSet mutableCopy();

    @Override
    default ImmutableLongSet immutableCopy() {
        throw new UnsupportedOperationException(); //TODO
    }

}
