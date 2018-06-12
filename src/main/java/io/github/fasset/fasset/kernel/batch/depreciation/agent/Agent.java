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
import io.github.fasset.fasset.model.FixedAsset;

import java.time.YearMonth;

/**
 * General representation of an object that given the {@link FixedAsset} and the {@link YearMonth} will
 * return the desired object to the caller
 *
 * @param <T> Type of object to be calculated at invocation
 */
public interface Agent<T> {

    /**
     * This method takes the following parameters and returns desired object to the caller
     *
     * @param asset    {@link FixedAsset} being depreciated
     * @param month    {@link YearMonth} in which this AccruedDepreciation is effective
     * @param proceeds {@link DepreciationProceeds} items to hold the values calculated from depreciation
     * @return Computed item from the above parameters
     */
    T invoke(FixedAsset asset, YearMonth month, DepreciationProceeds proceeds);
}
