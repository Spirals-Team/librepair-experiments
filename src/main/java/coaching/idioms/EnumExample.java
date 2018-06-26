
package coaching.idioms;

/**
 * Enum Example with textValues.
 */
public enum EnumExample {

    /** The Unknown. */
    Unknown("Unknown"),
    /** The Heads. */
    Heads("Heads"),
    /** The Tails. */
    Tails("Tails");

    /** The text value. */
    private String textValue;

    /**
     * Instantiates a new enum example.
     *
     * @param textValue
     *            the text value
     */
    EnumExample(final String textValue) {
        this.textValue = textValue;
    }

    /**
     * text.
     *
     * @return the text
     */
    public String getText() {
        return this.textValue;
    }

    /**
     * Factory method Enum value from string.
     *
     * @param textValue
     *            the text value
     * @return the enum example
     */
    public static EnumExample fromString(final String textValue) {
        for (final EnumExample instance : EnumExample.values()) {
            if (instance.textValue.equalsIgnoreCase(textValue)) {
                return instance;
            }
        }
        return null;
    }
}
