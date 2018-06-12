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

import io.github.fasset.fasset.kernel.batch.depreciation.DepreciationProceeds;
import io.github.fasset.fasset.model.Depreciation;

import java.util.List;

/**
 * This objects extracts and saves data into the {@link io.github.fasset.fasset.repository.DepreciationRepository}
 *
 * @author edwin.njeru
 */
public interface DepreciationService {

    /**
     * Saves the {@link Depreciation} object given as parameter to the {@link io.github.fasset.fasset.repository.DepreciationRepository}
     *
     * @param depreciation item to be saved
     */
    void saveDepreciation(Depreciation depreciation);

    /**
     * Saves all items in the list
     *
     * @param depreciationList Collection of depreciation items to be saved
     */
    void saveAllDepreciationItems(List<Depreciation> depreciationList);

    /**
     * @return Return the number of distinct sols
     */
    int getDistinctSolIds();

    /**
     * Saves multiple items using multiple repositories for items encapsulated in the
     * DepreciationProceeds object
     *
     * @param list of depreciationProceeds
     */
    void saveAllDepreciationProceeds(List<DepreciationProceeds> list);
}
