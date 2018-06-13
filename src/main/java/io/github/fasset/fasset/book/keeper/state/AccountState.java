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
package io.github.fasset.fasset.book.keeper.state;

import io.github.fasset.fasset.book.keeper.balance.AccountBalance;
import io.github.fasset.fasset.book.keeper.balance.AccountSide;
import io.github.ghacupha.cash.Cash;

/**
 * The Account could either be in {@link AccountSide#CREDIT} or {@link AccountSide#DEBIT}
 * This interface maintains the methods common to all these states to allow
 * reuse
 *
 * @author edwin.njeru
 */
public interface AccountState {

    /**
     * Get AccountBalance given the sum of debits and sum of credits
     *
     * @param debits  Total Cash amounts of debits
     * @param credits Total CAsh amounts of credits
     * @return Account balance for the account Containing Cash and AccountSide
     */
    AccountBalance getAccountBalance(Cash debits, Cash credits);

    /**
     * @return {@code AccountSide} of the Account
     */
    AccountSide getAccountSide();
}
