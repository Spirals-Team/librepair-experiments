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

import io.github.fasset.fasset.book.keeper.AccountingEntry;
import io.github.fasset.fasset.model.FixedAsset;

import java.util.List;

/**
 * This object will be used to abstract acquisition transactions for an asset, using the Asset account and
 * the Sundry Creditors account. This simplifies the story, of how the asset was acquired and, and how much
 * was paid to a vendor, since such, is subject to withholding tax obligations and sometimes contract-related
 * provisions, as such we are only interest with the financial representation of assets in this application.
 * In accordance to the Account Proliferation policy version 1.0, each asset is represented by an entry to the
 * respective Asset account.
 * Depreciation and acquisition could be done in bulk for sensitive things like disposal and revaluation it has
 * been thought better to post as individual items which will warrant like another interface altogether
 *
 * @author edwin.njeru
 */
public interface BatchEntryResolver {

    /**
     * Generates {@code AccountingEntry} items based on {@code FixedAsset} items passed in the parameter args.
     * The method creates entries for both {@code DEBIT} and {@code CREDIT} side. In this case SundryCreditors
     * account is credited and an Asset account is DEBITED with the full purchase cost of the asset.
     * When an account is being depreciated this method creates postings for {@code DEBIT} in the depreciation
     * account and {@code CREDIT} in the accumulated depreciation account
     *
     * @param fixedAssets Collection of {@code FixedAsset} items from which we are to generate entries
     * @return List containing Entry bookings for the fixedAssets passed in the parameter
     */
    List<AccountingEntry> resolveEntries(List<FixedAsset> fixedAssets);

}
