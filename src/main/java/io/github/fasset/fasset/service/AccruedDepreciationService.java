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
package io.github.fasset.fasset.service;

import io.github.fasset.fasset.model.AccruedDepreciation;
import io.github.fasset.fasset.model.FixedAsset;
import org.javamoney.moneta.Money;

import java.time.YearMonth;
import java.util.List;

/**
 * Service for data retrieval from database for {@link AccruedDepreciation}
 */
public interface AccruedDepreciationService {

    /**
     * Returns the accruedDepreciationAmount for a fixed asset and month given as param
     *
     * @param asset Asset being depreciated
     * @param month in which the accrued depreciation is effective
     * @return Accrued depreciation for asset
     */
    Money getAccruedDepreciationForAsset(FixedAsset asset, YearMonth month);

    /**
     * Saves the {@link AccruedDepreciation} object given in the parameter
     *
     * @param accruedDepreciation Accrued depreciation to be saved
     */
    void saveAccruedDepreciation(AccruedDepreciation accruedDepreciation);

    /**
     * Saves a {@link List} collection of {@link AccruedDepreciation} items
     *
     * @param items Collection of accruedDepreciation objects to be saved
     */
    void saveAllAccruedDepreciationRecords(List<? extends AccruedDepreciation> items);
}
