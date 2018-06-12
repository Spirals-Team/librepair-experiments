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
package io.github.fasset.fasset.accounts.depreciation;

import io.github.fasset.fasset.accounts.AccountResolver;
import io.github.fasset.fasset.book.keeper.Account;
import io.github.fasset.fasset.model.FixedAsset;
import org.springframework.stereotype.Component;

/**
 * This resolver generates the appropriate accounts required to post depreciation {@code DEBIT} and {@code CREDIT} items
 * as would be typical where the fixed asset is maintained at cost. Although this could be configured to maintain the asset
 * in the register at revaluation.
 *
 * @author edwin.njeru
 */
@Component("depreciationAccountResolver")
public class DepreciationAccountResolver implements AccountResolver {

    /* (non-Javadoc)
     * @see io.github.fasset.fasset.accounts.AccountResolver#resolveDebitAccount(io.github.fasset.fasset.model.FixedAsset)
     */
    @Override
    public Account resolveDebitAccount(FixedAsset fixedAsset) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see io.github.fasset.fasset.accounts.AccountResolver#resolveCreditAccount(io.github.fasset.fasset.model.FixedAsset)
     */
    @Override
    public Account resolveCreditAccount(FixedAsset fixedAsset) {
        // TODO Auto-generated method stub
        return null;
    }

}
