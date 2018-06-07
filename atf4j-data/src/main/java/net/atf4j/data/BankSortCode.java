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
import net.atf4j.core.Verify;

/**
 * BankSortCode data class. UK Bank
 * SortCode @”^\d{6,6}$|^\d{2,2}\-\d{2,2}\-\d{2,2}$”;
 */
public final class BankSortCode extends TestResultsReporting {

    /** The Constant PATTERN. */
    private static final Pattern PATTERN = Pattern.compile("^(\\d){2}-(\\d){2}-(\\d){2}$");

    /** The bank name. */
    private final String bankName = "";

    /** The sortcode. */
    private String sortcode = "00:00:00";

    /**
     * Create new INSTANCE of create.
     *
     * @return the bank sort code
     */
    public static BankSortCode create() {
        return new BankSortCode();
    }

    /**
     * Random.
     *
     * @return the bank sort code
     */
    public static BankSortCode random() {
        return null;
    }

    /**
     * Instantiates a new bank sort code.
     */
    private BankSortCode() {
        super();
        setBankSortCode("00-00-00");
    }

    /**
     * Instantiates a new bank sort code.
     *
     * @param bankSortCode the bank sort code
     */
    public BankSortCode(final String bankSortCode) {
        super();
        setBankSortCode(bankSortCode);
    }

    /**
     * Sets the bank sort code.
     *
     * @param bankSortCode the bank sort code
     * @return the bank sort code
     */
    public BankSortCode setBankSortCode(final String bankSortCode) {
        verifyNotNull(bankSortCode);
        this.sortcode = bankSortCode;
        return this;
    }

    /**
     * Gets the bank sort code.
     *
     * @return the bankSortCode as String.
     */
    public String getBankSortCode() {
        return this.sortcode;
    }

    /**
     * Verify the sort code format.
     *
     * @param sortcode the sortcode
     * @return true, if successful, otherwise false.
     */
    public static boolean verify(final String sortcode) {
        Verify.verifyNotNull(sortcode);
        final Matcher matcher = PATTERN.matcher(sortcode);
        return matcher.find();
    }

    /**
     * Debug string.
     *
     * @return the string
     */
    public String debugString() {
        return String.format("BankSortCode [bankName=%s, sortcode=%s]", this.bankName, this.sortcode);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.sortcode;
    }

}
