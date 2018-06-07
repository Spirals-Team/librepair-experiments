/**
 * This file is part of Automated Testing Framework for Java (atf4j).
 *
 * Atf4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Atf4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with atf4j.  If not, see http://www.gnu.org/licenses/.
 */

package net.atf4j.pog;

/**
 * A class to represent a WebElement decorated by FindBy annotation.
 */
public class WebElementField extends ClassField {

    /** FIND_BY_FORMAT Annotation. */
    private static final String FIND_BY_FORMAT = "@FindBy(%s = \"%s\")";

    /** FULL_FORMAT. */
    private static final String FULL_FORMAT = "\t%s\n\t%s\n";

    /** WEB_ELEMENT_TYPE. */
    private static final String WEB_ELEMENT_TYPE = "WebElement";

    /** Strategy. */
    private Strategy strategy = null;

    /** Locator. */
    private String locator = null;

    /**
     * Strategy Enum.
     */
    public enum Strategy {

        /** unknown. */
        UNKNOWN("Unknown"),

        /** id. */
        ID("id"),

        /** name. */
        NAME("name"),

        /** class name. */
        CLASS_NAME("className"),

        /** xpath. */
        XPATH("xpath"),

        /** css. */
        CSS("css"),

        /** link text. */
        LINK_TEXT("linkText"),

        /** partial link text. */
        PARTIAL_LINK_TEXT("partialLinkText");

        /** value. */
        private final String value;

        /**
         * Instantiates a new strategy.
         *
         * @param asText the as text
         */
        Strategy(final String asText) {
            value = asText;
        }

        /**
         * Initialise.
         *
         * @return the strategy
         */
        public static Strategy initialise() {
            return Strategy.UNKNOWN;
        }

        /**
         * Strategy from String value.
         *
         * @param asText the as text
         * @return Status value
         */
        public static Strategy fromString(final String asText) {
            for (final Strategy candidate : values()) {
                if (candidate.equals(asText)) {
                    return candidate;
                }
            }
            return null;
        }

        /*
         * (non-Javadoc)
         *
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return value;
        }
    }

    /**
     * Instantiates a new web element field.
     */
    public WebElementField() {
        super(WEB_ELEMENT_TYPE, "webElement");
    }

    /**
     * Instantiates a new page web element.
     *
     * @param strategy the strategy
     * @param locator the locator
     * @param fieldName the field name
     */
    public WebElementField(final Strategy strategy, final String locator, final String fieldName) {
        super(WEB_ELEMENT_TYPE, fieldName);
        setAccessModifier(AccessModifier.PROTECTED);
        setStrategy(strategy);
        setLocator(locator);
    }

    /**
     * Sets the locator strategy.
     *
     * @param strategy the strategy
     * @return the page web element
     */
    public WebElementField setStrategy(final Strategy strategy) {
        verifyNotNull(strategy);
        this.strategy = strategy;
        return this;
    }

    /**
     * Sets the locator strategy.
     *
     * @param strategy the strategy
     * @return the page web element
     */
    public WebElementField setStrategy(final String strategy) {
        verifyNotNull(strategy);
        this.strategy = Strategy.fromString(strategy);
        return this;
    }

    /**
     * Sets the locator.
     *
     * @param locator the locator
     * @return the page web element
     */
    public WebElementField setLocator(final String locator) {
        verifyNotNull(locator);
        this.locator = locator;
        return this;
    }

    /**
     * Gets the locator type.
     *
     * @return the locator type
     */
    public Strategy getLocatorType() {
        return strategy;
    }

    /**
     * Gets the locator.
     *
     * @return the locator
     */
    public String getLocator() {
        return String.format(FIND_BY_FORMAT, strategy, locator);
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.pog.ClassField#toCode()
     */
    public String toCode() {
        final String locatorLine = String.format(FIND_BY_FORMAT, strategy, locator);
        return String.format(FULL_FORMAT, locatorLine, super.getField());
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.pog.ClassField#debugString()
     */
    @Override
    public String debugString() {
        return String.format("%s [strategy=%s, locator=%s]",
                this.getClass().getSimpleName(),
                strategy,
                locator);
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.pog.ClassField#toString()
     */
    @Override
    public String toString() {
        if (log.isDebugEnabled()) {
            log.debug(debugString());
        }
        return toCode();
    }
}
