package net.coljate.counter;

import net.coljate.collection.ConcurrentCollection;
import net.coljate.counter.impl.ConcurrentHashCounter;

/**
 *
 * @author Ollie
 */
public interface ConcurrentCounter<T>
        extends MutableCounter<T>, ConcurrentCollection<T> {

    @Override
    ConcurrentCounter<T> mutableCopy();

    static <T> ConcurrentCounter<T> create() {
        return ConcurrentHashCounter.create();
    }

}
