package net.coljate.collection;

import net.coljate.util.Hashing;
import net.coljate.util.Strings;
import net.coljate.sequence.Sequence;

/**
 *
 * @author Ollie
 */
public abstract class AbstractCollection<T> implements Collection<T> {

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ":[" + Strings.elementsToString(this) + "]";
    }

    @Override
    @SuppressWarnings("override.param.invalid")
    public boolean equals(final Object that) {
        return this == that
                || (that instanceof Collection && this.equals((Collection<?>) that));
    }

    protected abstract boolean equals(Collection<?> that);

    @Override
    public int hashCode() {
        return this instanceof Sequence
                ? Hashing.orderedHash(this)
                : Hashing.unorderedHash(this);
    }

}
