
package patterns.command;

import java.util.Set;

/**
 * ParametersInterface Interface.
 */
public interface ParametersInterface {

    /**
     * prameter.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     * @return the abstract parameters
     */
    AbstractCommandParameters setParameter(final String key, final String value);

    /**
     * String property names.
     *
     * @return the sets the
     */
    Set<String> stringPropertyNames();

    /**
     * Value for.
     *
     * @param key
     *            the key
     * @return the string
     */
    String valueFor(final String key);

    /**
     * Value for.
     *
     * @param key
     *            the key
     * @param defaultValue
     *            the default value
     * @return the boolean
     */
    Boolean valueFor(final String key, final Boolean defaultValue);

    /**
     * Value for.
     *
     * @param key
     *            the key
     * @param defaultValue
     *            the default value
     * @return the string
     */
    String valueFor(final String key, final String defaultValue);

    /**
     * Value for.
     *
     * @param key
     *            the key
     * @param defaultValue
     *            the default value
     * @return the long
     */
    Long valueFor(final String key, final Long defaultValue);

}
