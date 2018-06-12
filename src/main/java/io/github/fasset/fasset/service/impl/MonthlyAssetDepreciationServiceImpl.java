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
package io.github.fasset.fasset.service.impl;

import io.github.fasset.fasset.model.depreciation.MonthlyAssetDepreciation;
import io.github.fasset.fasset.repository.MonthlyAssetDepreciationRepository;
import io.github.fasset.fasset.service.MonthlyAssetDepreciationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link MonthlyAssetDepreciationService} implementation
 */
@Service("monthlyAssetDepreciationService")
public class MonthlyAssetDepreciationServiceImpl implements MonthlyAssetDepreciationService {


    private final MonthlyAssetDepreciationRepository monthlyAssetDepreciationRepository;

    @Autowired
    public MonthlyAssetDepreciationServiceImpl(@Qualifier("monthlyAssetDepreciationRepository") MonthlyAssetDepreciationRepository monthlyAssetDepreciationRepository) {
        this.monthlyAssetDepreciationRepository = monthlyAssetDepreciationRepository;
    }

    /**
     * Return an ordered list of all monthly depreciation from the
     * monthlyAssetDepreciationRepository
     *
     * @return {@link List} of {@link MonthlyAssetDepreciation} entities from the repository
     */
    @Override
    public List<MonthlyAssetDepreciation> fetchAllMonthlyDepreciations() {
        return monthlyAssetDepreciationRepository.findAll().parallelStream().sorted(Comparator.comparing(MonthlyAssetDepreciation::getYear)).collect(Collectors.toList());
    }

    /**
     * Returns the MonthlyAssetDepreciation for a given assetId and Year
     *
     * @param fixedAssetId Id of the asset whose month on month depreciation we require
     * @param year         Year of depreciation for the asset
     * @return this
     */
    @Override
    @Cacheable("monthlyAssetDepreciationByIdAnYears")
    public MonthlyAssetDepreciation getMonthlyAssetDepreciationByAssetIdAndYear(int fixedAssetId, int year) {

        return monthlyAssetDepreciationRepository.findFirstByAssetIdAndYearEquals(fixedAssetId, year);
    }

    /**
     * Saves all new monthly depreciation items and updates exiting ones
     *
     * @param items Entities to be persisted into the repository
     */
    @Override
    public void saveAllMonthlyDepreciationItems(List<? extends MonthlyAssetDepreciation> items) {

        /*Set<MonthlyAssetDepreciation> filter = new UnifiedSet<>();

        filter.addAll(monthlyAssetDepreciationRepository.findAll());

        List<MonthlyAssetDepreciation> savedItems = new FastList<>();*/

        /*items
                .stream()
                .filter(i -> !monthlyAssetDepreciationRepository.exists(i.getId()))
                .forEach(unsavedItems::add);*/
        /*items
                .stream()
                .filter(filter::contains)
                .forEach(savedItems::add);

        //items.removeAll(savedItems);

        savedItems.forEach(i -> monthlyAssetDepreciationRepository.deleteById(i.getId()));*/

        monthlyAssetDepreciationRepository.saveAll(items);
    }
}
