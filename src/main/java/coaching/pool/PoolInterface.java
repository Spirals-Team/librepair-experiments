
package coaching.pool;

/**
 * Pool Interface.
 *
 * @param <E>
 *            the element type
 */
public interface PoolInterface<E> {

    /**
     * Adds element to the pool.
     *
     * @param element
     *            the element
     * @return the pool interface
     */
    PoolInterface<E> add(final E element);

    /**
     * Count the number of free elements.
     *
     * @return the int
     */
    int countFree();

    /**
     * Count the number used elements.
     *
     * @return the int
     */
    int countUsed();

    /**
     * Take an element from the pool.
     *
     * @return the e
     */
    E take();

    /**
     * Release element back to pool.
     *
     * @param t
     *            the t
     * @return the pool interface
     */
    PoolInterface<E> release(final E t);

    /**
     * remove an from the pool.
     *
     * @param element
     *            the element
     * @return the pool interface
     */
    PoolInterface<E> remove(final E element);

}
