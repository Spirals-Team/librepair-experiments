
package coaching.bags;

/**
 * Bag Interface with a fluent interface.
 */
public interface BagInterface {

    /**
     * bag with String values.
     *
     * @param values
     *            the values
     * @return this bag for a fluent interface
     */
    BagInterface fill(final String... values);

    /**
     * Adds an item to the bag.
     *
     * @param value
     *            the value
     * @return true, if successful, otherwise false., otherwise false.
     */
    boolean add(final String value);

    /**
     * pick item from the bag.
     *
     * @return the string
     */
    String pick();

    /**
     * choose item from bag.
     *
     * @return the string
     */
    String choose();

    /**
     * reset the back to its initial state.
     *
     * @return this bag for a fluent interface
     */
    BagInterface reset();

}
