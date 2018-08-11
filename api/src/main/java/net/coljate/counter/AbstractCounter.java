package net.coljate.counter;

import net.coljate.collection.AbstractCollection;
import net.coljate.collection.Collection;
import net.coljate.util.Equality;

/**
 *
 * @author Ollie
 */
public abstract class AbstractCounter<T>
        extends AbstractCollection<T>
        implements Counter<T> {

    @Override
    protected boolean equals(final Collection<?> that) {
        return that instanceof Counter
                && this.equals((Counter<?>) that);
    }

    protected boolean equals(final Counter<?> that) {
        return Equality.unorderedEquals(this, that);
    }

}
