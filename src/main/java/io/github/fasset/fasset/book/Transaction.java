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
package io.github.fasset.fasset.book;

import io.github.fasset.fasset.book.keeper.Account;
import io.github.fasset.fasset.book.keeper.AccountingEntry;
import io.github.fasset.fasset.book.keeper.balance.AccountSide;
import io.github.fasset.fasset.book.keeper.util.ImmutableEntryException;
import io.github.fasset.fasset.book.keeper.util.MismatchedCurrencyException;
import io.github.fasset.fasset.book.keeper.util.UnableToPostException;
import io.github.ghacupha.cash.Cash;
import io.github.ghacupha.time.point.TimePoint;

import java.util.Map;
import java.util.Set;

/**
 * A collection of {@link AccountingEntry} items being posted at the same time
 */
public interface Transaction {

    /**
     * @param accountSide     {@link AccountSide} in which the entry is for
     * @param amount          Monetary value of the entry
     * @param account         {@link Account} into which the {@link AccountingEntry} is posted
     * @param narration       {@link AccountingEntry} specifications of the {@link AccountingEntry}
     * @param entryAttributes Map containing additional info about the entry
     * @throws ImmutableEntryException     when you addEntry to a posted transaction
     * @throws MismatchedCurrencyException when the {@code Account}, {@code AccountingEntry} or {@code Transaction} currencies
     *                                     do not match
     */
    void addEntry(AccountSide accountSide, Cash amount, Account account, String narration, Map<String, String> entryAttributes) throws ImmutableEntryException, MismatchedCurrencyException;

    /**
     * The add method adds entries to the transaction provided the transaction has not already
     * been posted
     *
     * @param accountSide     to which the entry is being posted
     * @param amount          {@link Cash} amount being posted to the journal
     * @param account         {@link Account} into which the {@link AccountingEntry} is being added
     * @param narration       a brief narration of the entry
     * @param entryAttributes Map containing additional details about the entry being entered
     * @param date            posting date of the entry
     * @throws ImmutableEntryException     when you addEntry to a posted transaction
     * @throws MismatchedCurrencyException when the {@code Account}, {@code AccountingEntry} or {@code Transaction} currencies
     *                                     do not match
     */
    void addEntry(AccountSide accountSide, Cash amount, Account account, String narration, Map<String, String> entryAttributes, TimePoint date)
        throws ImmutableEntryException, MismatchedCurrencyException;

    /**
     * The add method adds entries to the transaction provided the transaction has not already
     * been posted
     *
     * @param accountSide to which the entry is being posted
     * @param amount      {@link Cash} amount being posted to the journal
     * @param account     {@link Account} into which the {@link AccountingEntry} is being added
     * @param narration   a brief narration of the entry
     * @param date        posting date of the entry
     * @throws ImmutableEntryException     when you addEntry to a posted transaction
     * @throws MismatchedCurrencyException when the {@code Account}, {@code AccountingEntry} or {@code Transaction} currencies
     *                                     do not match
     */
    void addEntry(AccountSide accountSide, Cash amount, Account account, String narration, TimePoint date) throws ImmutableEntryException, MismatchedCurrencyException;

    /**
     * Experimental method for adding a fully formed {@code AccountingEntry}
     *
     * @param entry Fully formed Entry for addition to this
     * @throws MismatchedCurrencyException when the entry's currency differs from the Transaction's currency
     * @throws ImmutableEntryException     when the Transaction is already posted at the time the entry is being added
     */
    void addEntry(AccountingEntry entry) throws MismatchedCurrencyException, ImmutableEntryException;

    /**
     * Same method as {code Transaction.addEntry()} but with an empty map as description of the
     * entry. The {@code AccountingEntry} can therefore only be distinguished by its narration.
     * The add method adds entries to the transaction provided the transaction has not already
     * been posted
     *
     * @param accountSide to which the entry is being posted
     * @param amount      {@link Cash} amount being posted to the journal
     * @param account     {@link Account} into which the {@link AccountingEntry} is being added
     * @param narration   a brief narration of the entry
     * @throws ImmutableEntryException     when you addEntry to a posted transaction
     * @throws MismatchedCurrencyException when the {@code Account}, {@code AccountingEntry} or {@code Transaction} currencies
     */
    void addEntry(AccountSide accountSide, Cash amount, Account account, String narration) throws ImmutableEntryException, MismatchedCurrencyException;

    /**
     * Adds the {@link AccountingEntry} items into the accounts involved in this {@link Transaction}.
     * This method will not run if it is already posted
     *
     * @throws UnableToPostException   when the transaction could not be posted because the amount is not balanced
     * @throws ImmutableEntryException when an AccountingEntry is added to a posted transaction
     */
    void post() throws UnableToPostException, ImmutableEntryException;

    /**
     * @return Collection of {@link AccountingEntry} items in the Transaction
     */
    Set<AccountingEntry> getEntries();
}
