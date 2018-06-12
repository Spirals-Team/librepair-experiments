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

import io.github.fasset.fasset.model.depreciation.MonthlyAssetDepreciation;

import java.util.List;

/**
 * Service for data retrieval from database for {@link MonthlyAssetDepreciation}
 */
public interface MonthlyAssetDepreciationService {

    /**
     * Return an ordered list of all monthly depreciation from the
     * monthlyAssetDepreciationRepository
     *
     * @return Collection of MonthlyAssetDepreciation entities from the database
     */
    List<MonthlyAssetDepreciation> fetchAllMonthlyDepreciations();

    /**
     * Returns the MonthlyAssetDepreciation for a given assetId and Year
     *
     * @param fixedAssetId Id of FixedAsset for whose MonthlyAssetDepreciation we need
     * @param year         Year of depreciation for the asset
     * @return MonthlyAssetDepreciation for a FixedAsset entity whose nomenclature was found in the database
     */
    MonthlyAssetDepreciation getMonthlyAssetDepreciationByAssetIdAndYear(int fixedAssetId, int year);

    /**
     * Saves all new monthly depreciation items and updates exiting ones
     *
     * @param items Collection of monthlyDepreciation entities to be saved in the repository
     */
    void saveAllMonthlyDepreciationItems(List<? extends MonthlyAssetDepreciation> items);
}
