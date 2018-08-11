package net.coljate.set;

import java.util.Spliterator;
import java.util.Spliterators;

import net.coljate.collection.ConcurrentCollection;
import net.coljate.set.impl.ConcurrentWrappedSet;

/**
 *
 * @author Ollie
 */
public interface ConcurrentSet<T>
        extends MutableSet<T>, ConcurrentCollection<T> {

    @Override
    default ConcurrentSet<T> mutableCopy() {
        return ConcurrentWrappedSet.copyIntoHashSet(this);
    }

    @Override
    default Spliterator<T> spliterator() {
        return Spliterators.spliterator(this.iterator(), this.count(), Spliterator.DISTINCT | Spliterator.CONCURRENT);
    }

    static <T> ConcurrentSet<T> createHashSet() {
        return ConcurrentWrappedSet.createHashSet();
    }

    static <T> ConcurrentSet<T> createHashSet(final int initialCapacity) {
        return ConcurrentWrappedSet.createHashSet(initialCapacity);
    }

}
