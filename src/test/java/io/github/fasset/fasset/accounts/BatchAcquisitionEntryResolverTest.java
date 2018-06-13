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
package io.github.fasset.fasset.accounts;

import io.github.fasset.fasset.book.keeper.Account;
import io.github.fasset.fasset.book.keeper.AccountingEntry;
import io.github.fasset.fasset.book.keeper.AccountingTransaction;
import io.github.fasset.fasset.book.keeper.balance.AccountBalance;
import io.github.fasset.fasset.book.keeper.util.ImmutableEntryException;
import io.github.fasset.fasset.book.keeper.util.MismatchedCurrencyException;
import io.github.fasset.fasset.model.FixedAsset;
import io.github.ghacupha.time.point.SimpleDate;
import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static io.github.fasset.fasset.book.keeper.balance.AccountSide.CREDIT;
import static io.github.fasset.fasset.book.keeper.balance.AccountSide.DEBIT;
import static io.github.ghacupha.cash.HardCash.shilling;
import static io.github.ghacupha.time.point.SimpleDate.on;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class BatchAcquisitionEntryResolverTest {

    private final static Currency KES = Currency.getInstance("KES");
    private final static FixedAsset radio = new FixedAsset("Radio", Money.of(200, "KES"), "Electronics", "001", LocalDate.of(2018, 2, 5), "abc01", Money.of(9.5, "KES"));
    private final static FixedAsset lenovo = new FixedAsset("Lenovo IDP110", Money.of(5600, "KES"), "COMPUTERS", "987", LocalDate.of(2018, 2, 13), "abc02", Money.of(13.42, "KES"));
    private final static FixedAsset chair = new FixedAsset("Chair", Money.of(156, "KES"), "FURNITURE", "010", LocalDate.of(2018, 1, 13), "abc03", Money.of(19.24, "KES"));
    // Mock account
    private static final Account electronics = new Account("Electronics", "101", DEBIT, Currency.getInstance("KES"), SimpleDate.of(2018, 1, 1));
    private static final Account computers = new Account("Computers", "102", DEBIT, Currency.getInstance("KES"), SimpleDate.of(2018, 1, 1));
    private static final Account furniture = new Account("Furnitures", "103", DEBIT, Currency.getInstance("KES"), SimpleDate.of(2018, 1, 1));
    private static final Account sundryCreditors = new Account("Sundry Creditors", "104", CREDIT, Currency.getInstance("KES"), SimpleDate.of(2018, 1, 1));
    private BatchEntryResolver batchEntryResolver;
    private List<FixedAsset> fixedAssets = new ArrayList<>();

    @Before
    public void setUp() throws Exception {

        fixedAssets.add(radio);
        fixedAssets.add(lenovo);
        fixedAssets.add(chair);

        AccountResolver accountResolver = Mockito.mock(AccountResolver.class);
        when(accountResolver.resolveDebitAccount(radio)).thenReturn(electronics);
        when(accountResolver.resolveDebitAccount(lenovo)).thenReturn(computers);
        when(accountResolver.resolveDebitAccount(chair)).thenReturn(furniture);
        when(accountResolver.resolveCreditAccount(radio)).thenReturn(sundryCreditors);
        when(accountResolver.resolveCreditAccount(lenovo)).thenReturn(sundryCreditors);
        when(accountResolver.resolveCreditAccount(chair)).thenReturn(sundryCreditors);


        batchEntryResolver = new BatchAcquisitionEntryResolver(accountResolver);
    }

    @Test
    public void numberOfEntriesGenerated() throws Exception {

        List<AccountingEntry> entries = batchEntryResolver.resolveEntries(fixedAssets);

        // each asset is represented by an entry
        assertEquals(fixedAssets.size() * 2, entries.size());
    }

    @Test
    public void entriesCanBePosted() throws Exception {

        List<AccountingEntry> entries = batchEntryResolver.resolveEntries(fixedAssets);

        AccountingTransaction testPostingAssets = AccountingTransaction.create("Test posting entry resolver", on(2018, 2, 21), Currency.getInstance("KES"));

        entries.forEach(entry -> {
            try {
                testPostingAssets.addEntry(entry);
            } catch (MismatchedCurrencyException | ImmutableEntryException e) {
                e.printStackTrace();
            }
        });

        testPostingAssets.post();


        // each asset is represented by an entry
        assertEquals(fixedAssets.size() * 2, entries.size());
        assertEquals(new AccountBalance(shilling(200), DEBIT), electronics.balance(on(2018, 2, 21)));
        assertEquals(new AccountBalance(shilling(5600), DEBIT), computers.balance(on(2018, 2, 21)));
        assertEquals(new AccountBalance(shilling(156), DEBIT), furniture.balance(on(2018, 2, 21)));
        assertEquals(new AccountBalance(shilling(5956), CREDIT), sundryCreditors.balance(on(2018, 2, 21)));
    }
}