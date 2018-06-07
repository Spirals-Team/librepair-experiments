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

package net.atf4j.data.factory;

import java.util.Random;

import net.atf4j.core.TestResultsReporting;
import net.atf4j.core.Text;
import net.atf4j.csv.CsvRow;
import net.atf4j.data.Postcode;
import net.atf4j.data.PostcodeData;

/**
 * A factory for creating PostcodeData objects.
 */
public final class PostcodeDataFactory extends TestResultsReporting {

    /** POSTCODE_DATA_FACTORY. */
    private static final PostcodeDataFactory POSTCODE_DATA_FACTORY = new PostcodeDataFactory();

    /** POSTCODE_DATA. */
    private static final PostcodeData POSTCODE_DATA = PostcodeData.getInstance();

    /** The random. */
    protected static Random random = new Random(System.currentTimeMillis());

    /**
     * Gets the single instance of PostcodeDataFactory.
     *
     * @return single instance of PostcodeDataFactory
     */
    public static PostcodeDataFactory getInstance() {
        return PostcodeDataFactory.POSTCODE_DATA_FACTORY;
    }

    /**
     * Private constructor prevents wild instantiation.
     */
    private PostcodeDataFactory() {
        super();
    }

    /**
     * Create new INSTANCE of Postcode.
     *
     * @return the postal address
     */
    public static Postcode create() {
        return new Postcode();
    }

    /**
     * Create a random postcode.
     *
     * @return the Postcode object.
     */
    public static Postcode random() {
        final Postcode postcode = new Postcode();
        final int rowCount = POSTCODE_DATA.rowCount();
        final int randomRow = random.nextInt(rowCount);
        final CsvRow row = POSTCODE_DATA.getRow(randomRow);
        final String[] fields = row.getFields();
        postcode.setOutwardCode(fields[0]);
        final String inward = String.format("%c%C%C",
                Text.randomDigit(),
                Text.randomChar(),
                Text.randomChar());
        postcode.setInwardCode(inward);
        return postcode;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("PostcodeDataFactory [postCodeData=%s]", POSTCODE_DATA);
    }

}
