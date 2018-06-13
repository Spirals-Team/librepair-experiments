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
package io.github.fasset.fasset.kernel.batch.depreciation.agent;

import io.github.fasset.fasset.kernel.batch.depreciation.DepreciationProceeds;
import io.github.fasset.fasset.model.AccruedDepreciation;
import io.github.fasset.fasset.model.FixedAsset;

import java.time.YearMonth;

/**
 * Calculates AccruedDepreciation
 */
public interface AccruedDepreciationAgent extends Agent<AccruedDepreciation> {

    /**
     * @param asset    {@link FixedAsset} being depreciated
     * @param month    {@link YearMonth} in which this AccruedDepreciation is effective
     * @param proceeds {@link DepreciationProceeds} items to hold the values calculated from depreciation
     * @return Computed item for AccruedDepreciation
     */
    @Override
    AccruedDepreciation invoke(FixedAsset asset, YearMonth month, DepreciationProceeds proceeds);
}
