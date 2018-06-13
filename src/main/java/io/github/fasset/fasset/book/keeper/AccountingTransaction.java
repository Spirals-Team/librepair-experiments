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

import io.github.fasset.fasset.book.Transaction;
import io.github.fasset.fasset.book.keeper.balance.AccountSide;
import io.github.fasset.fasset.book.keeper.util.ImmutableEntryException;
import io.github.fasset.fasset.book.keeper.util.MismatchedCurrencyException;
import io.github.fasset.fasset.book.keeper.util.UnableToPostException;
import io.github.ghacupha.cash.Cash;
import io.github.ghacupha.time.point.TimePoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.github.fasset.fasset.book.keeper.balance.AccountSide.CREDIT;
import static io.github.fasset.fasset.book.keeper.balance.AccountSide.DEBIT;
import static java.util.Collections.EMPTY_MAP;


/**
 * Immutable implementation of the {@link Transaction} interface once created nothing about it can change, except
 * addition of entries. The underlying {@link Collection} cannot be re-assigned once created, and is implemented by
 * {@link List} whose implementation involved a data structure that copies itself for every mutative procedure that
 * is done, in this case involving addition of {@link AccountingEntry} items. There is a boolean that says whether or not the
 * {@link Transaction} has been posted, which is dangerously non final, but is volatile nevertheless.
 *
 * @author edwin.njeru
 */
public final class AccountingTransaction implements Transaction {

    private static final Logger log = LoggerFactory.getLogger(AccountingTransaction.class);

    private final String label;

    private final TimePoint date;
    private final Currency currency;
    private final List<AccountingEntry> entries = new CopyOnWriteArrayList<>();
    private volatile boolean wasPosted;

    AccountingTransaction(String label, TimePoint date, Currency currency) {

        this.label = label;
        this.date = date;
        this.currency = currency;

        log.info("AccountingTransaction created {}", this);
    }

    public static AccountingTransaction create(String label, TimePoint date, Currency currency) {
        return new AccountingTransaction(label, date, currency);
    }

    private static Double mapCashToDouble(AccountingEntry accountingEntry) {
        return accountingEntry.getAmount().getNumber().doubleValue();
    }

    private static boolean predicateCredits(AccountingEntry accountingEntry) {
        boolean credit;
        log.trace("Checking if accountingEntry {} is credit ", accountingEntry);
        credit = accountingEntry.getAccountSide() == CREDIT;
        log.trace("AccountingEntry : {} is credit {}", accountingEntry, credit);
        return credit;
    }

    private static boolean predicateDebits(AccountingEntry accountingEntry) {
        boolean debit;
        log.trace("Checking if accountingEntry {} is debit ", accountingEntry);
        debit = accountingEntry.getAccountSide() == DEBIT;
        log.trace("AccountingEntry : {} is credit {}", accountingEntry, debit);
        return debit;
    }

    /**
     * The add method adds entries to the transaction provided the transaction has not already
     * been posted
     *
     * @param accountSide     to which the entry is being posted
     * @param amount          {@link Cash} amount being posted to the journal
     * @param account         {@link Account} into which the {@link AccountingEntry} is being added
     * @param narration       a brief narration of the entry
     * @param entryAttributes Map containing additional details about the entry being entered
     * @throws ImmutableEntryException     when you addEntry to a posted transaction
     * @throws MismatchedCurrencyException when the {@code Account}, {@code AccountingEntry} or {@code Transaction} currencies
     *                                     do not match
     */
    @Override
    public void addEntry(AccountSide accountSide, Cash amount, Account account, String narration, Map<String, String> entryAttributes) throws ImmutableEntryException, MismatchedCurrencyException {

        this.addEntry(accountSide, amount, account, narration, entryAttributes, this.date);
    }

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
    @Override
    public void addEntry(AccountSide accountSide, Cash amount, Account account, String narration, Map<String, String> entryAttributes, TimePoint date)
        throws ImmutableEntryException, MismatchedCurrencyException {

        log.debug("Attempting to add entry {} amount of : {} in account : {} narration : {}", accountSide, amount, account, narration);
        // assign currency
        if (wasPosted) {
            throw new ImmutableEntryException("Cannot add entry to a transaction that's already posted");
        } else if (!account.getCurrency().equals(this.currency) || !amount.getCurrency().equals(this.currency)) {
            throw new MismatchedCurrencyException("Cannot add entry whose getCurrency differs to that of the transaction");
        } else {
            log.debug("Adding entry  : {} into transaction : {}", narration, this);
            AccountingEntry tempAccountingEntry = new AccountingEntry(date, account, narration, accountSide, amount);

            // Add attributes to the entry
            if (!entryAttributes.isEmpty()) {
                entryAttributes.forEach(tempAccountingEntry::addAttribute);
            }

            entries.add(tempAccountingEntry);

            log.debug("AccountingEntry {} has been added to {}", tempAccountingEntry, this);
        }
    }

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
    @Override
    public void addEntry(AccountSide accountSide, Cash amount, Account account, String narration, TimePoint date) throws ImmutableEntryException, MismatchedCurrencyException {

        this.addEntry(accountSide, amount, account, narration, EMPTY_MAP, date);
    }

    /**
     * Experimental method for adding a fully formed {@code AccountingEntry}
     *
     * @param entry Fully formed Entry for addition to this
     */
    @Override
    public void addEntry(AccountingEntry entry) throws MismatchedCurrencyException, ImmutableEntryException {

        checkNotNull(entry.getAccount(), "Each entry must have an explicitly declared account to which we are posting it");

        this.addEntry(entry.getAccountSide(), entry.getAmount(), entry.getAccount(), entry.getNarration(), entry.getEntryAttributes(),
            entry.getBookingDate() == null ? this.date : entry.getBookingDate());
    }

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
    @Override
    public void addEntry(AccountSide accountSide, Cash amount, Account account, String narration) throws ImmutableEntryException, MismatchedCurrencyException {
        this.addEntry(accountSide, amount, account, narration, EMPTY_MAP, this.date);
    }

    /**
     * Posts the transactions into respective {@link Account} items
     *
     * @throws UnableToPostException   {@link UnableToPostException} thrown when the transaction is not balanced
     *                                 That is if the items posted on the debit are more than those posted on the credit or vice versa.
     * @throws ImmutableEntryException when an AccountingEntry is added to a posted transaction
     */
    @Override
    public void post() throws UnableToPostException, ImmutableEntryException {

        if (wasPosted) {
            throw new ImmutableEntryException(String.format("The transaction : %s is already posted", this.label));
        }

        if (balanced() != 0) {

            if (balanced() > 0) {

                throw new UnableToPostException(String.format("The debits are more than credits by : %s", balanced()));

            } else {

                throw new UnableToPostException(String.format("The credits are more than debits by : %s", balanced() * -1));
            }

        } else {

            log.debug("Posting : {} entries ...", entries.size());

            entries.parallelStream().forEach(AccountingEntry::post);

            wasPosted = true;
        }
    }

    private double balanced() {

        double debits = entries.parallelStream().filter(AccountingTransaction::predicateDebits).map(AccountingTransaction::mapCashToDouble).reduce(0.00, (acc, val) -> acc + val);

        return debits - entries.parallelStream().filter(AccountingTransaction::predicateCredits).map(AccountingTransaction::mapCashToDouble).reduce(0.00, (acc, val) -> acc + val);
    }

    @Override
    public Set<AccountingEntry> getEntries() {

        return Collections.unmodifiableSet(new CopyOnWriteArraySet<>(entries));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountingTransaction that = (AccountingTransaction) o;
        return wasPosted == that.wasPosted && Objects.equals(label, that.label) && Objects.equals(date, that.date) && Objects.equals(currency, that.currency) && Objects.equals(entries, that.entries);
    }

    @Override
    public int hashCode() {

        return Objects.hash(label, date, wasPosted, currency, entries);
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append('\'').append(label).append('\'');
        sb.append(", date=").append(date);
        sb.append(", currency=").append(currency);
        sb.append(", entries=").append(entries);
        sb.append('}');
        return sb.toString();
    }
}
