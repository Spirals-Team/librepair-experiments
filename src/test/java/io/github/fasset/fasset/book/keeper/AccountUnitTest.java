/*
 * fassets - Project for light-weight tracking of fixed assets
 * Copyright © 2018 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.fasset.fasset.book.keeper;

import io.github.fasset.fasset.book.keeper.balance.AccountBalance;
import io.github.fasset.fasset.book.keeper.util.MismatchedCurrencyException;
import io.github.fasset.fasset.book.keeper.util.UnEnteredDetailsException;
import io.github.fasset.fasset.book.keeper.util.UntimelyBookingDateException;
import io.github.ghacupha.cash.HardCash;
import io.github.ghacupha.time.point.TimePoint;
import org.junit.Before;
import org.junit.Test;

import java.util.Currency;
import java.util.Map;

import static io.github.fasset.fasset.book.keeper.AccountAttribute.ACCOUNT_TYPE;
import static io.github.fasset.fasset.book.keeper.AccountAttribute.CONTRA_ACCOUNT;
import static io.github.fasset.fasset.book.keeper.AccountAttribute.GENERAL_LEDGER;
import static io.github.fasset.fasset.book.keeper.balance.AccountSide.DEBIT;
import static io.github.ghacupha.cash.HardCash.dollar;
import static io.github.ghacupha.time.point.SimpleDate.on;
import static org.junit.Assert.assertEquals;

public class AccountUnitTest {

    private Account account;

    @Before
    public void setUp() throws Exception {

        account = new Account("Test Account", "TST001", DEBIT, Currency.getInstance("USD"), on(2018, 12, 20));

        account.addAttribute(GENERAL_LEDGER, "4875");
    }

    @Test(expected = UnEnteredDetailsException.class)
    public void youCannotGetUnenteredDetails() throws Exception {

        account.addAttribute(CONTRA_ACCOUNT, "Accumulated Test Account");

        assertEquals("Asset Account", account.getAttribute(ACCOUNT_TYPE));
    }

    @Test
    public void addAttribute() throws Exception {

        account.addAttribute(CONTRA_ACCOUNT, "Accumulated Test Account");

        assertEquals("Accumulated Test Account", account.getAttribute(CONTRA_ACCOUNT));
    }

    @Test
    public void addEntry() throws Exception {

        account.addEntry(new AccountingEntry(on(2018, 12, 21), account, "AccountingEntry Test 1", DEBIT, dollar(115.23)));
        account.addEntry(new AccountingEntry(on(2018, 12, 23), account, "AccountingEntry Test 2", DEBIT, dollar(110.23)));
        account.addEntry(new AccountingEntry(on(2018, 12, 24), account, "AccountingEntry Test 3", DEBIT, dollar(95.23)));
        account.addEntry(new AccountingEntry(on(2018, 12, 28), account, "AccountingEntry Test 4", DEBIT, dollar(90.23)));

        assertEquals(new AccountBalance(dollar(320.69), DEBIT), account.balance(2018, 12, 25));
    }

    @Test(expected = UntimelyBookingDateException.class)
    public void bookingDateOnlyAfterAccountOpening() throws Exception {

        account.addEntry(new AccountingEntry(on(2018, 12, 21), account, "AccountingEntry Test 1", DEBIT, dollar(115.23)));
        account.addEntry(new AccountingEntry(on(2018, 12, 24), account, "AccountingEntry Test 3", DEBIT, dollar(95.23)));
        account.addEntry(new AccountingEntry(on(2018, 12, 19), account, "AccountingEntry Test 4", DEBIT, dollar(90.23)));

        // Exception thrown no need for assertion
    }

    @Test(expected = MismatchedCurrencyException.class)
    public void entryCurrencyMustMatchAccount() throws Exception {

        account.addEntry(new AccountingEntry(on(2018, 12, 21), account, "AccountingEntry Test 1", DEBIT, dollar(115.23)));
        account.addEntry(new AccountingEntry(on(2018, 12, 24), account, "AccountingEntry Test 3", DEBIT, HardCash.shilling(95.23)));
        account.addEntry(new AccountingEntry(on(2018, 12, 19), account, "AccountingEntry Test 4", DEBIT, dollar(90.23)));// test never reaches here

        // Exception thrown no need for assertion
    }

    @Test
    public void balance() throws Exception {

        account.addEntry(new AccountingEntry(on(2018, 12, 20), account, "AccountingEntry Test 1", DEBIT, dollar(115.23)));
        account.addEntry(new AccountingEntry(on(2018, 12, 23), account, "AccountingEntry Test 2", DEBIT, dollar(110.23)));
        account.addEntry(new AccountingEntry(on(2018, 12, 24), account, "AccountingEntry Test 3", DEBIT, dollar(95.23)));
        account.addEntry(new AccountingEntry(on(2018, 12, 26), account, "AccountingEntry Test 4", DEBIT, dollar(90.23)));

        assertEquals(new AccountBalance(dollar(410.92), DEBIT), account.balance(2018, 12, 27));

    }

    @Test
    public void openingDateRemainsUnchanged() throws Exception {

        TimePoint openingDate = account.getOpeningDate();

        assertEquals(on(2018, 12, 20), openingDate);

        // Adding 50 days to the opening date of the account
        openingDate.addDays(50);

        // Opening date should not have changed
        assertEquals(on(2018, 12, 20), openingDate);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void accountAttributesAreImmutable() throws Exception {

        Map<AccountAttribute, String> attributes = account.getAccountAttributes();

        assertEquals("4875", attributes.get(GENERAL_LEDGER));

        attributes.put(GENERAL_LEDGER, "4846");

        assertEquals("4875", attributes.get(GENERAL_LEDGER));
    }
}