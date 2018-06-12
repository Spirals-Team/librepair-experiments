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

import io.github.fasset.fasset.book.keeper.AccountingEntry;
import io.github.fasset.fasset.model.FixedAsset;

import java.util.List;

/**
 * This is interface is similar with the BatchEntryResolver however the depreciation concept must have Effective period
 * and such was lacking in that interface. A new interface must therefore be created to represent Effectivity of depreciation
 *
 * @author edwin.njeru
 */
public interface DepreciationEntryResolver {

    /**
     * Generates {@code AccountingEntry} items based on {@code FixedAssets} items passed in the parameter.
     * The method will generate both {@code DEBIT} and {@code CREDIT} side entries and will abstract from
     * client the logic of obtaining depreciation rates and values from configurations in the application
     *
     * @param fixedAssets Items to be depreciated
     * @param period      when the depreciation Entries are effective
     * @return {@code AccountingEntries} to post depreciation
     */
    List<AccountingEntry> resolveEntries(List<FixedAsset> fixedAssets, DepreciationPeriod period);
}
