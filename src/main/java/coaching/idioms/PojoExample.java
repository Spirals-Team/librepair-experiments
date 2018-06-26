
package coaching.idioms;

/**
 * Example plain old java object class.
 */
public final class PojoExample implements Cloneable {

    /** The string value. */
    private String stringValue;

    /** The long value. */
    private long longValue;

    /**
     * Instantiates a new pojo example.
     */
    public PojoExample() {
        super();
    }

    /**
     * Instantiates a new pojo example.
     *
     * @param stringValue
     *            the string value
     * @param longValue
     *            the long value
     */
    public PojoExample(final String stringValue, final long longValue) {
        super();
        setStringValue(stringValue);
        setLongValue(longValue);
    }

    /**
     * Sets the string value.
     *
     * @param stringValue
     *            the string value
     * @return this instance for a fluent interface.
     */
    public PojoExample setStringValue(final String stringValue) {
        this.stringValue = stringValue;
        return this;
    }

    /**
     * Sets the long value.
     *
     * @param longValue
     *            the long value
     * @return this instance for a fluent interface.
     */
    public PojoExample setLongValue(final long longValue) {
        this.longValue = longValue;
        return this;
    }

    /**
     * Gets the string value.
     *
     * @return the string value
     */
    public String getStringValue() {
        return this.stringValue;
    }

    /**
     * Gets the long value.
     *
     * @return the long value
     */
    public long getLongValue() {
        return this.longValue;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("PojoExample [stringValue=%s, longValue=%s]", this.stringValue, this.longValue);
    }

}
