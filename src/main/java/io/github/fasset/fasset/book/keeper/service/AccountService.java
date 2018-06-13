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
package io.github.fasset.fasset.book.keeper.service;

import io.github.fasset.fasset.book.keeper.Account;

import java.util.Collection;

/**
 * Provides api for saving, deleting and updating {@code PersistentAccount} objects
 */
public interface AccountService {

    /**
     * Saves the item passed to the parameter
     *
     * @param persistentAccount Object to be saved in the repository
     * @return The newly saved item from the repository
     */
    Account saveAccount(Account persistentAccount);

    /**
     * Saves items in a  {@code Collection} of {@code PersistentAccount} items to the
     * repository
     *
     * @param persistentAccounts {@link Account} items to be saved
     */
    void saveAccounts(Collection<Account> persistentAccounts);

    /**
     * Returns the Account instance whose nomenclature is given in the parameter
     *
     * @param accountId Of the account whose instance we are fetching from the repo
     * @return Requested Account instance
     */
    Account fetchAccountById(int accountId);
}
