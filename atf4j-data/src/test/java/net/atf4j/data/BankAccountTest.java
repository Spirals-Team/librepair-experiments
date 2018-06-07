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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * A Unit test class for BankAccount objects.
 */
public final class BankAccountTest extends TestResultsReporting {

    /**
     * Unit Test for BankAccount constructor with null string.
     */
    @Test
    public void testBankAccountNullString() {
        final String nullString = null;
        final BankAccount bankAccount = new BankAccount(nullString);
        verifyNotNull(bankAccount);
    }

    /**
     * Unit Test for BankAccount constructor with null object.
     */
    @Test
    public void testBankAccountNullBank() {
        final Bank nullBank = null;
        final BankAccount bankAccount = new BankAccount(nullBank);
        verifyNotNull(bankAccount);
    }

    /**
     * Unit Test for BankAccount constructor with empty string.
     */
    @Test
    public void testBankAccountString() {
        final BankAccount bankAccount = new BankAccount("");
        verifyNotNull(bankAccount);
    }

    /**
     * Unit Test for BankAccount.
     */
    @Test
    public void testCreate() {
        final BankAccount bankAccount = BankAccount.create();
        verifyNotNull(bankAccount);
    }

    /**
     * Unit Test for BankAccount.
     */
    @Test
    public void testRandom() {
        final BankAccount bankAccount = BankAccount.random();
        verifyNotNull(bankAccount);
    }

    /**
     * Unit Test for BankAccount.
     */
    @Test
    public void testSetGetBank() {
        final Bank testBank = new Bank("Test Bank");
        final BankAccount bankAccount = new BankAccount(testBank);
        verifyNotNull(bankAccount);
        assertEquals(testBank, bankAccount.setBank(testBank).getBank());
    }

    /**
     * Unit Test for BankAccount.
     */
    @Test
    public void testSetGetAccountNo() {
        final BankAccount bankAccount = BankAccount.create();
        verifyNotNull(bankAccount);
        final String accountNo = "00001234";
        assertEquals(accountNo, bankAccount.setAccountNo(accountNo).getAccountNo());
    }

    /**
     * Test debug string.
     */
    @Test
    public void testdebugString() {
        final BankAccount bankAccount = BankAccount.create();
        verifyNotNull(bankAccount);
        final String string = bankAccount.debugString();
        verifyNotNull(string);
        this.log.debug("bankAccount.debugString() = {}", string);
    }

    /**
     * Unit Test for BankAccount.
     */
    @Test
    public void testToString() {
        final BankAccount bankAccount = BankAccount.create();
        verifyNotNull(bankAccount);
        final String string = bankAccount.toString();
        this.log.error("bankAccount.toString() = {}", string);
    }
}
