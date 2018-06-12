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
import io.github.fasset.fasset.model.FixedAsset;

/**
 * Provides Account for posting various scenarios as demanded by clients
 */
public interface AccountResolver {

    /**
     * Generates appropriate Account for the asset passed in the parameter, when we are posting simple transactions
     * like acquisition or depreciation
     *
     * @param fixedAsset for which we seek an appropriate Account
     * @return Account appropriate for the recording of transaction for the parameter
     * fixedAsset
     */
    Account resolveDebitAccount(FixedAsset fixedAsset);

    /**
     * Generates appropriate credit Account for the asset passed in the parameter, when we are posting simple transactions
     * like acquisition or depreciation
     *
     * @param fixedAsset for which we seek an appropriate Account
     * @return Account appropriate for the recording of transaction for the parameter
     * fixedAsset
     */
    Account resolveCreditAccount(FixedAsset fixedAsset);
}
