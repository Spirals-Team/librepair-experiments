package net.coljate.list;

import java.util.Spliterator;
import java.util.Spliterators;

import net.coljate.collection.ConcurrentCollection;
import net.coljate.list.impl.WrappedConcurrentLinkedDeque;

/**
 *
 * @author Ollie
 */
public interface ConcurrentList<T>
        extends MutableList<T>, ConcurrentCollection<T> {

    @Override
    ConcurrentList<T> mutableCopy();

    @Override
    default Spliterator<T> spliterator() {
        return Spliterators.spliterator(this.iterator(), this.count(), Spliterator.ORDERED | Spliterator.CONCURRENT);
    }

    static <T> ConcurrentList<T> createConcurrentLinkedList() {
        return WrappedConcurrentLinkedDeque.create();
    }

}
