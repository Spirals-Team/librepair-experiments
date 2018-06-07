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

/**
 * Business data.
 */
public class Business extends Name {

    /** business postal address. */
    private PostalAddress address = new PostalAddress();

    /**
     * Factory method to create a new instance of Business.
     *
     * @return new instance of Business
     */
    public static Business create() {
        return new Business();
    }

    /**
     * Random.
     *
     * @return the business
     */
    public static Business random() {
        return new Business();
    }

    /**
     * Instantiates a new business.
     */
    protected Business() {
        super();
    }

    /**
     * Instantiates a new business.
     *
     * @param name the string
     */
    public Business(final String name) {
        super(name);
    }

    /**
     * Instantiates a new business.
     *
     * @param name the name
     * @param address the address
     */
    public Business(final String name, final PostalAddress address) {
        super(name);
        setAddress(address);
    }

    /**
     * Sets the address.
     *
     * @param address the address
     * @return the business
     */
    public Business setAddress(final PostalAddress address) {
        verifyNotNull(address);
        this.address = address;
        return this;
    }

    /**
     * Sets the address.
     *
     * @param address the address
     * @return the business
     */
    public Business setAddress(final String[] address) {
        verifyNotNull(address);
        this.address.setAddressLines(address);
        return this;
    }

    /**
     * Sets the address row.
     *
     * @param lineNo the line no
     * @param addressLine the address line
     */
    public void setAddressRow(final int lineNo, final String addressLine) {
        verifyNotNull(lineNo);
        verifyNotNull(addressLine);
        this.address.setAddressRow(lineNo, addressLine);
    }

    /**
     * Sets the postcode.
     *
     * @param postcode the postcode
     * @return the postal address
     */
    public PostalAddress setPostcode(final Postcode postcode) {
        verifyNotNull(postcode);
        return this.address.setPostcode(postcode);
    }

    /**
     * Gets the address.
     *
     * @return the address
     */
    public PostalAddress getAddress() {
        return this.address;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.data.Name#toString()
     */
    @Override
    public String toString() {
        return String.format("Business [address=%s]", this.address);
    }

}
