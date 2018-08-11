package net.coljate.counter;

import net.coljate.collection.MutableCollection;
import net.coljate.counter.impl.MutableHashCounter;

/**
 *
 * @author Ollie
 */
public interface MutableCounter<T>
        extends Counter<T>, MutableCollection<T> {

    /**
     *
     * @param element
     * @param count a new count to set.
     * @throws IllegalArgumentException if the count is negative.
     */
    void set(T element, int count);

    /**
     * Increment the count of an element by the given amount
     *
     * @param element
     * @param amount
     * @return the new value.
     */
    int incrementAndGet(T element, int amount);

    default int incrementAndGet(final T element) {
        return this.incrementAndGet(element, 1);
    }

    default int getAndIncrement(final T element) {
        return this.incrementAndGet(element) - 1;
    }

    default int getAndIncrement(final T element, final int amount) {
        return this.incrementAndGet(element, amount) - amount;
    }

    default int incrementIfPresent(final Object element) {
        return this.contains(element)
                ? this.incrementAndGet((T) element)
                : 0;
    }

    int decrementAndGet(T element, int amount);

    default int decrementAndGet(final T element) {
        return this.decrementAndGet(element, 1);
    }

    @Override
    @Deprecated
    default boolean add(final T element) {
        this.incrementAndGet(element);
        return true;
    }

    @Override
    default boolean removeFirst(final Object element) {
        final int count = this.count(element);
        if (count > 0) {
            this.decrementAndGet((T) element);
            return true;
        } else {
            return false;
        }
    }

    @Override
    default int removeAll(final Object element) {
        final int count = this.count(element);
        if (count > 0) {
            this.decrementAndGet((T) element, count);
            return 1;
        } else {
            return 0;
        }
    }

    static <T> MutableCounter<T> copyOf(final Iterable<? extends T> counter) {
        return MutableHashCounter.copyOf(counter);
    }

    static <T> MutableCounter<T> copyOf(final Counter<? extends T> counter) {
        return MutableHashCounter.copyOf(counter);
    }

}
