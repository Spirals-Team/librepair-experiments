/**
 * Created on 06-Jul-2004
 */

package coaching.model;

/**
 * Person Interface.
 */
public interface PersonInterface {

    /**
     * set the person's name.
     *
     * @param name
     *            the name
     * @return this for a fluent interface.
     */
    PersonInterface setName(final String name);

    /**
     * Gets the name.
     *
     * @return the name
     */
    String getName();

}
