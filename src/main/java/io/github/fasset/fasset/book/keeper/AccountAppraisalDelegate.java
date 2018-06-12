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
import io.github.fasset.fasset.book.keeper.balance.AccountSide;
import io.github.fasset.fasset.book.keeper.state.AccountCreditState;
import io.github.fasset.fasset.book.keeper.state.AccountDebitState;
import io.github.fasset.fasset.book.keeper.state.AccountState;
import io.github.ghacupha.cash.Cash;
import io.github.ghacupha.cash.HardCash;
import io.github.ghacupha.time.point.DateRange;

import java.util.List;

import static io.github.fasset.fasset.book.keeper.balance.AccountSide.CREDIT;
import static io.github.fasset.fasset.book.keeper.balance.AccountSide.DEBIT;

/**
 * Okay so then we had to expose the {@link AccountSide} against better advise since calling the {@link Account#balance}
 * method is going to be an expensive method, which could most likely trigger a circular dependency loop. There needs to be a method
 * for getting the current {@code AccountSide} without gritting your teeth. So uncle Bob please forgive me for I have sinned,
 * but there is just no practical inexpensive way of doing this stuff, and still be able to use this delegate for any
 * {@link Account} implementation. This delegate is designed to serve up a balance for any {@link Account} implementation
 *
 * @author edwin.njeru
 */
public class AccountAppraisalDelegate {

    private final Account account;
    private AccountState accountSideState;

    AccountAppraisalDelegate(Account account) {

        this.account = account;
        AccountState debitAccountState = new AccountDebitState(this.account);
        AccountState creditAccountState = new AccountCreditState(this.account);

        this.accountSideState = account.getAccountSide() == DEBIT ? debitAccountState : creditAccountState;
    }

    public AccountBalance balance(DateRange dateRange) {

        Cash debits = getDebits(dateRange, account.getEntries());

        Cash credits = getCredits(dateRange, account.getEntries());

        if (debits.isZero() || credits.isZero()) {
            if (!debits.isZero() && credits.isZero()) {
                return new AccountBalance(debits, DEBIT);
            } else if (debits.isZero() && !credits.isZero()) {
                return new AccountBalance(credits, CREDIT);
            }
        } else {

            return accountSideState.getAccountBalance(debits, credits);
        }

        return new AccountBalance(HardCash.of(0.0, account.getCurrency()), account.getAccountSide());
    }

    private Cash getCredits(DateRange dateRange, List<AccountingEntry> accountEntries) {
        return HardCash.of(accountEntries.parallelStream().filter(entry -> dateRange.includes(entry.getBookingDate())).filter(entry -> entry.getAccountSide() == CREDIT)
            .map(entry -> entry.getAmount().getNumber().doubleValue()).reduce(0.00, (acc, value) -> acc + value), account.getCurrency());
    }

    private Cash getDebits(DateRange dateRange, List<AccountingEntry> accountEntries) {
        return HardCash.of(accountEntries.parallelStream().filter(entry -> dateRange.includes(entry.getBookingDate())).filter(entry -> entry.getAccountSide() == DEBIT)
            .map(entry -> entry.getAmount().getNumber().doubleValue()).reduce(0.00, (acc, value) -> acc + value), account.getCurrency());
    }
}
