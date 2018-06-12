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

import io.github.fasset.fasset.book.AccountDomainModel;
import io.github.fasset.fasset.book.keeper.balance.AccountSide;
import io.github.fasset.fasset.book.keeper.util.MismatchedCurrencyException;
import io.github.fasset.fasset.book.keeper.util.UnEnteredDetailsException;
import io.github.fasset.fasset.book.keeper.util.UntimelyBookingDateException;
import io.github.ghacupha.cash.Cash;
import io.github.ghacupha.time.point.TimePoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import java.util.Currency;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This model maps unit of data entered into an account. It contains {@code bookingDate} when the transaction
 * is posted, the {@code account} into which it is posted, the {@code narration}, the {@code amount} and the
 * {@code accountSide} into which we are posting. The rest of attributes are added into the {@code entryAttributes}
 * field.
 */
@Entity(name = "AccountingEntry")
@Table(name = "accounting_entry")
public class AccountingEntry extends AccountDomainModel<String> {

    private static final Logger log = LoggerFactory.getLogger(AccountingEntry.class);

    // Ref TimePointAttributeConverter.class
    @Column
    private TimePoint bookingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account")
    private Account account;

    @Column
    private String narration;

    @Enumerated
    private AccountSide accountSide;

    // Ref CashAttributeConverter
    @Column
    private Cash amount;

    //@MapKeyEnumerated(EnumType.STRING)
    @ElementCollection
    @MapKeyColumn(name = "entry_attribute_name", length = 100)
    @Column(name = "entry_attribute")
    @CollectionTable(name = "entry_attributes", joinColumns = @JoinColumn(name = "accounting_entry_id"))
    private Map<String, String> entryAttributes = new ConcurrentHashMap<>();

    public AccountingEntry() {
    }

    public AccountingEntry(TimePoint bookingDate, Account account, String narration, AccountSide accountSide, Cash amount) {
        this.bookingDate = bookingDate;
        this.account = account;
        this.narration = narration;
        this.accountSide = accountSide;
        this.amount = amount;
    }

    /**
     * Adds unstructured additional attributes as required into the entry
     *
     * @param entryAttribute {@link EntryAttribute} identity of the attribute in an entry
     * @param attribute      the object content being registered as an attribute
     */
    public void addAttribute(String entryAttribute, String attribute) {
        this.entryAttributes.put(entryAttribute, attribute);
    }

    /**
     * @param entryAttribute identification of the attribute we wish to obtain from the entry
     * @return Object containing the attribute value
     * @throws UnEnteredDetailsException When an attribute is enquired from the AccountingEntry before it has been added
     *                                   into the AccountingEntry
     */
    public String getAttribute(String entryAttribute) throws UnEnteredDetailsException {

        if (!this.entryAttributes.containsKey(entryAttribute)) {
            throw new UnEnteredDetailsException(String.format("Exception: %s has not been added to entry : %s", entryAttribute.toString(), toString()));
        }

        return this.entryAttributes.get(entryAttribute);
    }

    /**
     * @return Currency of the monetary amounts to be save in this
     */
    public Currency getCurrency() {
        return amount.getCurrency();
    }

    /**
     * @return {@link AccountSide} to which this AccountingEntry is aggregating the
     * Account balance
     */
    public AccountSide getAccountSide() {
        return accountSide;
    }

    public void setAccountSide(AccountSide accountSide) {
        this.accountSide = accountSide;
    }

    /**
     * @return Booking date of the AccountingEntry
     */
    TimePoint getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(TimePoint bookingDate) {
        this.bookingDate = bookingDate;
    }

    /**
     * @return The amount being posted into the Account and encapsulated
     * by the AccountingEntry
     */
    public Cash getAmount() {
        return amount;
    }

    public void setAmount(Cash amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Map<String, String> getEntryAttributes() {
        return entryAttributes;
    }

    public void setEntryAttributes(Map<String, String> entryAttributes) {
        this.entryAttributes = entryAttributes;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    /**
     * Assigns this AccountingEntry with a specific account into which it is aggregated as
     * {@code AccountBalance}
     */
    public void post() {

        try {
            log.debug("Adding entry : {} into account : {}", this, account);
            account.addEntry(this);
        } catch (UntimelyBookingDateException e) {
            log.error("Could not post the entry : {} into forAccount : {}", this, account, e.getStackTrace());

            log.error("Cause : the AccountingEntry booking date :{} is sooner than the forAccount's opening date {} ", bookingDate, account.getOpeningDate(), e.getStackTrace());
        } catch (MismatchedCurrencyException e) {
            log.error("Could not post the entry : {} into the forAccount : {} ", this, account, e.getStackTrace());
            log.error("Cause the entry's currency : {} does not match the forAccount's currency : {}", amount.getCurrency(), account.getCurrency(), e.getStackTrace());
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        AccountingEntry accountingEntry = (AccountingEntry) o;

        if (bookingDate != null ? !bookingDate.equals(accountingEntry.bookingDate) : accountingEntry.bookingDate != null) {
            return false;
        }
        if (!account.equals(accountingEntry.account)) {
            return false;
        }
        if (!narration.equals(accountingEntry.narration)) {
            return false;
        }
        if (accountSide != accountingEntry.accountSide) {
            return false;
        }
        if (amount != null ? !amount.equals(accountingEntry.amount) : accountingEntry.amount != null) {
            return false;
        }
        return entryAttributes != null ? entryAttributes.equals(accountingEntry.entryAttributes) : accountingEntry.entryAttributes == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (bookingDate != null ? bookingDate.hashCode() : 0);
        result = 31 * result + account.hashCode();
        result = 31 * result + narration.hashCode();
        result = 31 * result + accountSide.hashCode();
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (entryAttributes != null ? entryAttributes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return narration;
    }
}
