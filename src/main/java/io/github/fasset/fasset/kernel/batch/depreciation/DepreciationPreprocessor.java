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
package io.github.fasset.fasset.kernel.batch.depreciation;

import io.github.fasset.fasset.model.FixedAsset;
import org.javamoney.moneta.Money;

import java.time.YearMonth;

/**
 * Please note that no actual processing is done here. The interface reviews the asset
 * against the current depreciation amount and the date of purchase compared with the
 * month of depreciation.
 * After that is reviewed we return only the items that make sense in the business domain
 *
 * @author edwin.njeru
 */
public interface DepreciationPreprocessor {

    /**
     * @return Depreciation period as month
     */
    YearMonth getMonth();

    /**
     * Sets depreciation period in months
     *
     * @param month Month in which the depreciation is occurring
     * @return this
     */
    DepreciationPreprocessor setMonth(YearMonth month);

    /**
     * @return FixedAsset item being depreciated
     */
    FixedAsset getAsset();

    /**
     * Sets the asset to be reviewed for depreciation
     *
     * @param asset {@link FixedAsset} being depreciated
     * @return this
     */
    DepreciationPreprocessor setAsset(FixedAsset asset);

    /**
     * @return amount of depreciation
     */
    Money getDepreciationAmount();

    /**
     * Sets the amount of depreciation for review
     *
     * @param depreciationAmount {@link Money} amount of depreciation
     * @return this
     */
    DepreciationPreprocessor setDepreciationAmount(Money depreciationAmount);

    /**
     * This method ensures all properties are set and evaluated
     *
     * @return this
     */
    DepreciationPreprocessor setProperties();
}
