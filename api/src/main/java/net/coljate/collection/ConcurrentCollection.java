package net.coljate.collection;

import java.util.Spliterator;
import java.util.Spliterators;

/**
 *
 * @author Ollie
 */
public interface ConcurrentCollection<T> extends MutableCollection<T> {

    @Override
    ConcurrentCollection<T> mutableCopy();

    @Override
    default Spliterator<T> spliterator() {
        return Spliterators.spliterator(this.iterator(), this.count(), Spliterator.CONCURRENT);
    }

}
