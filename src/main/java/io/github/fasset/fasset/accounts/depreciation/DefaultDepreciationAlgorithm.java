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

import io.github.fasset.fasset.model.FixedAsset;
import io.github.ghacupha.cash.Cash;
import org.springframework.stereotype.Component;

/**
 * Work in progress on this one
 */
@Component("defaultDepreciationAlgorithm")
public class DefaultDepreciationAlgorithm implements DepreciationAlgorithm {

    /**
     * Calculates the {@code Cash} amount of depreciation given the Asset to be depreciated and the DepreciationPeriod.
     * At the very list it is expected that an object will be created that tracks the last time an asset was depreciated
     * amount of depreciation, the date of next depreciation date and the depreciation method
     *
     * @param asset  Fixed Asset to be depreciated
     * @param period THe period of depreciation
     * @return The amount of depreciation
     */
    @Override
    public Cash getDepreciation(FixedAsset asset, DepreciationPeriod period) {
        return null;
    }

    /**
     * @return Name of the algorithm
     */
    @Override
    public String name() {
        return null;
    }
}
