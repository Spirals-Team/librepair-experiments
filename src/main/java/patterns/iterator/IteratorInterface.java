
package patterns.iterator;

/**
 * Iterator Interface.
 */
public interface IteratorInterface {

    /**
     * First item.
     *
     * @return the item interface
     */
    ItemInterface first();

    /**
     * Current item.
     *
     * @return the item interface
     */
    ItemInterface currentItem();

    /**
     * Next item.
     *
     * @return the item interface
     */
    ItemInterface next();

    /**
     * Checks if is done.
     *
     * @return true, if successful, otherwise false., otherwise false.
     */
    boolean isDone();

}
