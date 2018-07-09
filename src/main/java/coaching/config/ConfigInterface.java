
package coaching.config;

/**
 * An Interface for Configuration classes.
 */
public interface ConfigInterface {

    /**
     * get property by key.
     *
     * @param key
     *            the name of key
     * @return the property value as a String object.
     */
    String get(final String key);

    /**
     * property, with default if not found.
     *
     * @param key
     *            the name of key
     * @param defaultValue
     *            the default value
     * @return the property value as a String object.
     */
    String get(final String key, final String defaultValue);

    /**
     * Value for key, allows an environment value to override the property.
     *
     * @param key the key
     * @param defaultValue the default value, if no property found.
     * @return the property value as a String object.
     */
    // String valueFor(final String key, final String defaultValue);

    /**
     * Value for key, allows an environment value to override the property.
     *
     * @param key the key
     * @return the value as a String object.
     */
    // String valueFor(final String key);

}
