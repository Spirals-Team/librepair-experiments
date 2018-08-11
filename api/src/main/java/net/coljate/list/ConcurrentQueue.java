package net.coljate.list;

import net.coljate.collection.ConcurrentCollection;

/**
 *
 * @author Ollie
 */
public interface ConcurrentQueue<T> extends Queue<T>, ConcurrentCollection<T> {

    @Override
    ConcurrentQueue<T> mutableCopy();

}
