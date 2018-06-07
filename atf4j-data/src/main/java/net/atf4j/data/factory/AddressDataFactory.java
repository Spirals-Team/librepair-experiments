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

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import net.atf4j.data.PostalAddress;
import net.atf4j.data.Postcode;

/**
 * Address Data Factory.
 */
public final class AddressDataFactory extends AbstractDataFactory {

    /** Default filename for ADDRESS_LINE_STEMS_TXT. */
    private static final String ADDRESS_LINE_STEMS_TXT = "addressLineStems.txt";

    /** Default filename for ADDRESS_LINE_POSTFIX_TXT. */
    private static final String ADDRESS_LINE_POSTFIX_TXT = "addressLinePostfix.txt";

    /** Default filename for ADDRESS_STEMS_TXT. */
    private static final String ADDRESS_STEMS_TXT = "addressStems.txt";

    /** Default filename for ADDRESS_LOCALS_TXT. */
    private static final String ADDRESS_LOCALS_TXT = "addressLocals.txt";

    /** The address data factory. */
    private static AddressDataFactory addressDataFactory = null;

    /** The address line stems. */
    private String[] addressLineStems;

    /** The address line postfix. */
    private String[] addressLinePostfix;

    /** The address locals. */
    private String[] addressLocals;

    /** The address stems. */
    private String[] addressStems;

    /**
     * Gets the single INSTANCE of AddressDataFactory.
     *
     * @return single INSTANCE of AddressDataFactory
     */
    public static AddressDataFactory getInstance() {
        if (AddressDataFactory.addressDataFactory == null) {
            AddressDataFactory.addressDataFactory = new AddressDataFactory();
        }
        return AddressDataFactory.addressDataFactory;
    }

    /**
     * Private constructor prevents wild instantiation.
     */
    private AddressDataFactory() {
        super();
        initialise();
    }

    /**
     * Initialise.
     */
    private void initialise() {
        this.addressLineStems = load(ADDRESS_LINE_STEMS_TXT);
        this.addressLinePostfix = load(ADDRESS_LINE_POSTFIX_TXT);
        this.addressStems = load(ADDRESS_STEMS_TXT);
        this.addressLocals = load(ADDRESS_LOCALS_TXT);
    }

    /**
     * Create new INSTANCE of create.
     *
     * @return the postal address
     */
    public static PostalAddress create() {
        return new PostalAddress();
    }

    /**
     * Random.
     *
     * @return the postal address
     */
    public static PostalAddress random() {
        final PostalAddress postalAddress = new PostalAddress();
        postalAddress.setAddressRow(1, null);
        postalAddress.setAddressRow(2, null);
        postalAddress.setPostcode(new Postcode());
        return postalAddress;
    }

    /**
     * Format data lines.
     *
     * @param data the data
     * @return the string
     */
    private String fromatData(final String data) {
        return data.replace("{", "{\n\t").replace(", ", "\n\t").replace("}", "\n\t}");
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        assertNotNull(this.addressLineStems);
        assertNotNull(this.addressLinePostfix);
        assertNotNull(this.addressStems);
        assertNotNull(this.addressLocals);

        return String.format(
                "AddressDataFactory [addressLineStems=%s, addressLinePostfix=%s, addressStems=%s, addressLocals=%s]",
                fromatData(Arrays.toString(this.addressLineStems)),
                fromatData(Arrays.toString(this.addressLinePostfix)),
                fromatData(Arrays.toString(this.addressStems)),
                fromatData(Arrays.toString(this.addressLocals)));
    }

}
