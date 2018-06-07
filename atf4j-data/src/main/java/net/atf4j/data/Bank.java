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
 * Bank data class.
 */
public final class Bank extends Business {

    /** The bank sort code. */
    private BankSortCode bankSortCode;

    /** The bank name. */
    private String bankName = "";

    /** IBAN. */
    private String iban = "";

    /**
     * Factory method to create a new instance of Bank.
     *
     * @return new instance of Bank
     */
    public static Bank create() {
        return new Bank();
    }

    /**
     * Random.
     *
     * @return the bank
     */
    public static Bank random() {
        return create();
    }

    /**
     * Instantiates a new bank.
     */
    public Bank() {
        super();
        setBankSortCode(BankSortCode.create());
        setName("Bank Name");
    }

    /**
     * Instantiates a new bank.
     *
     * @param sortcode the sortcode
     * @param name the name
     */
    public Bank(final BankSortCode sortcode, final String name) {
        super();
        setBankSortCode(this.bankSortCode);
        setName(this.bankName);
    }

    /**
     * Instantiates a new bank.
     *
     * @param bankName the bank name
     */
    public Bank(final String bankName) {
        super();
        this.bankSortCode = BankSortCode.create();
        setName(bankName);
    }

    /**
     * Sets the bank sort code.
     *
     * @param bankSortCode the bank sort code
     * @return the bank
     */
    public Bank setBankSortCode(final String bankSortCode) {
        verifyNotNull(bankSortCode);
        this.bankSortCode.setBankSortCode(bankSortCode);
        return this;
    }

    /**
     * Sets the bank sort code.
     *
     * @param bankSortCode the bankSortCode to set
     * @return the bank
     */
    public Bank setBankSortCode(final BankSortCode bankSortCode) {
        verifyNotNull(bankSortCode);
        this.bankSortCode = bankSortCode;
        return this;
    }

    /**
     * Sets the bank name.
     *
     * @param bankName the bank name
     * @return the bank
     */
    public Bank setBankName(final String bankName) {
        verifyNotNull(bankName);
        this.bankName = bankName;
        return this;
    }

    /**
     * Sets the iban.
     *
     * @param iban the iban
     * @return the bank
     */
    public Bank setIban(final String iban) {
        verifyNotNull(iban);
        this.iban = iban;
        return this;
    }

    /**
     * Gets the bank sort code.
     *
     * @return the bankSortCode
     */
    public BankSortCode getBankSortCode() {
        return this.bankSortCode;
    }

    /**
     * Gets the bank name.
     *
     * @return the bank name
     */
    public String getBankName() {
        return this.bankName;
    }

    /**
     * Gets the iban.
     *
     * @return the iban
     */
    public String getIban() {
        return this.iban;
    }

    /* (non-Javadoc)
     * @see net.atf4j.data.Business#toString()
     */
    @Override
    public String toString() {
        return String.format("Bank [bankName=%s, bankSortCode=%s, iban=%s]",
                this.bankName,
                this.bankSortCode,
                this.iban);
    }

}
