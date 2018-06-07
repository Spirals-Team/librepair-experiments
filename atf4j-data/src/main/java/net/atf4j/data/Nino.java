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

package net.atf4j.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.atf4j.core.TestResultsReporting;

/**
 * UK National Insurance number, commonly known as Nino.
 * ([0-9]){2}([0-9]){2}([0-9]){2}
 */
public final class Nino extends TestResultsReporting {

    /** REGULAR EXPRESSION. */
    private static final Pattern PATTERN = Pattern
        .compile("^[A-CEGHJ-PR-TW-Z]{1}[A-CEGHJ-NPR-TW-Z]{1}[- ]?[0-9]{2}[- ]?[0-9]{2}[- ]?[0-9]{2}[- ]?[A-D]?$");

    /** The nino. */
    private String nino = "";

    /**
     * Create new INSTANCE of create.
     *
     * @return the nino
     */
    public static Nino create() {
        return new Nino();
    }

    /**
     * Instantiates a new national insurance number.
     */
    public Nino() {
        super();
    }

    /**
     * Instantiates a new national insurance number.
     *
     * @param nino the code
     */
    public Nino(final String nino) {
        super();
        set(nino);
    }

    /**
     * Sets the code.
     *
     * @param nino the code to set
     * @return the national insurance
     */
    public Nino set(final String nino) {
        if (Nino.verify(nino)) {
            this.nino = nino;
        }
        return this;
    }

    /**
     * Verify.
     *
     * @param nino the code \* @return true, if successful, otherwise false.
     * @return true, if successful, otherwise false.
     */
    public static boolean verify(final String nino) {
        final Matcher matcher = PATTERN.matcher(nino);
        return matcher.find();
    }

    /**
     * Gets the code.
     *
     * @return the code
     */
    public String get() {
        return this.nino;
    }

    /**
     * Debug string.
     *
     * @return the string
     */
    public String debugString() {
        return String.format("Nino [nino=%s]", this.nino);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.nino;
    }
}
