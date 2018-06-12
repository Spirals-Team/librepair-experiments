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
package io.github.fasset.fasset.book.keeper.balance;

import com.google.common.collect.ImmutableList;
import io.github.fasset.fasset.book.keeper.util.MismatchedCurrencyException;
import org.junit.Test;

import java.util.List;

import static io.github.fasset.fasset.book.keeper.balance.AccountBalance.newBalance;
import static io.github.fasset.fasset.book.keeper.balance.AccountSide.CREDIT;
import static io.github.fasset.fasset.book.keeper.balance.AccountSide.DEBIT;
import static io.github.ghacupha.cash.HardCash.shilling;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AccountBalanceTest {

    private AccountBalance debitReference = newBalance(shilling(500), DEBIT);
    private AccountBalance creditReference = newBalance(shilling(500), CREDIT);

    private AccountBalance debit250 = newBalance(shilling(250), DEBIT);
    private AccountBalance credit250 = newBalance(shilling(250), CREDIT);
    private AccountBalance credit750 = newBalance(shilling(750), CREDIT);
    private AccountBalance debit750 = newBalance(shilling(750), DEBIT);

    private List<AccountBalance> balances2Add = ImmutableList.of(debit250, credit250, credit750);

    @Test
    public void addToCreditReference() throws MismatchedCurrencyException {

        assertEquals(newBalance(shilling(750), CREDIT), creditReference.add(credit250));
        assertEquals(newBalance(shilling(250), CREDIT), creditReference.add(debit250));
        assertEquals(newBalance(shilling(250), DEBIT), creditReference.add(debit750));

        balances2Add.parallelStream().forEach(bal -> {
            try {
                creditReference.add(bal);
            } catch (MismatchedCurrencyException e) {
                e.printStackTrace();
            }
        });

        // Reference balance is immutable
        assertEquals(newBalance(shilling(500), CREDIT), creditReference);
        assertEquals(newBalance(shilling(1250), CREDIT), creditReference.add(balances2Add));

        // You  could add debitReference to itself instead
        balances2Add.forEach(bal -> {
            try {
                creditReference = creditReference.add(bal);
            } catch (MismatchedCurrencyException e) {
                e.printStackTrace();
            }
        });
        assertEquals(newBalance(shilling(1250), CREDIT), creditReference);
    }

    @Test
    public void addToDebitReference() throws MismatchedCurrencyException {

        assertEquals(newBalance(shilling(750), DEBIT), debitReference.add(debit250));
        assertEquals(newBalance(shilling(250), DEBIT), debitReference.add(credit250));
        assertEquals(newBalance(shilling(250), CREDIT), debitReference.add(credit750));

        balances2Add.parallelStream().forEach(bal -> {
            try {
                debitReference.add(bal);
            } catch (MismatchedCurrencyException e) {
                e.printStackTrace();
            }
        });

        // Reference balance is immutable
        assertEquals(newBalance(shilling(500), DEBIT), debitReference);
        assertEquals(newBalance(shilling(250), CREDIT), debitReference.add(balances2Add));

        // You  could add debitReference to itself instead
        balances2Add.forEach(bal -> {
            try {
                debitReference = debitReference.add(bal);
            } catch (MismatchedCurrencyException e) {
                e.printStackTrace();
            }
        });
        assertNotEquals(newBalance(shilling(500), DEBIT), debitReference);
        assertEquals(newBalance(shilling(250), CREDIT), debitReference);
    }
}