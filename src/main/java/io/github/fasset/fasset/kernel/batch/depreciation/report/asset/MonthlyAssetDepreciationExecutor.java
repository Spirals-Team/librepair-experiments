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
package io.github.fasset.fasset.kernel.batch.depreciation.report.asset;

import io.github.fasset.fasset.model.FixedAsset;
import io.github.fasset.fasset.model.depreciation.MonthlyAssetDepreciation;

/**
 * This object takes a depreciation item and extracts from the
 * {@link io.github.fasset.fasset.service.MonthlyAssetDepreciationService} the corresponding
 * {@link io.github.fasset.fasset.model.depreciation.MonthlyAssetDepreciation} item. Which is then updated
 * using the depreciation data passed to the object
 *
 * @author edwin.njeru
 */
public interface MonthlyAssetDepreciationExecutor {

    /**
     * Returns {@link MonthlyAssetDepreciation} item that is updated with data from the depreciation
     * item
     *
     * @param fixedAsset {@link FixedAsset} being depreciated
     * @param year       in which the depreciation is taking place
     * @return MonthlyAssetDepreciation
     */
    MonthlyAssetDepreciation getMonthlyDepreciation(FixedAsset fixedAsset, Integer year);
}
