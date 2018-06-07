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

import net.atf4j.data.Bank;

/**
 * BankAccountFactory.
 */
public final class BankDataFactory extends AbstractDataFactory {

    /** The Constant BANK_DATA_CSV. */
    private static final String BANK_DATA_CSV = "sortcodesData.csv";

    /** The bank data factory. */
    private static BankDataFactory bankDataFactory = null;

    /**
     * Gets the instance.
     *
     * @return the instance
     */
    public static BankDataFactory getInstance() {
        if (BankDataFactory.bankDataFactory == null) {
            BankDataFactory.bankDataFactory = new BankDataFactory();
        }
        return BankDataFactory.bankDataFactory;
    }

    /**
     * Private constructor prevents wild instantiation.
     */
    private BankDataFactory() {
        super();
        initialise();
    }

    /**
     * Initialise.
     */
    protected void initialise() {
        this.lines = load(BANK_DATA_CSV);
    }

    /**
     * Create new INSTANCE of create.
     *
     * @return the bank
     */
    public static Bank create() {
        return new Bank();
    }

    /**
     * Random.
     *
     * @return the string
     */
    public static Bank random() {
        final Bank bank = new Bank();
        return bank;
    }

}
