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

import io.github.fasset.fasset.model.CategoryConfiguration;
import io.github.fasset.fasset.repository.CategoryConfigurationRepository;
import io.github.fasset.fasset.service.CategoryBriefService;
import io.github.fasset.fasset.service.CategoryConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@link CategoryBriefService} implementation
 */
@Service("categoryConfigurationService")
public class CategoryConfigurationServiceImpl implements CategoryConfigurationService {

    private static final Logger log = LoggerFactory.getLogger(CategoryConfigurationServiceImpl.class);


    private final CategoryConfigurationRepository categoryConfigurationRepository;

    @Autowired
    public CategoryConfigurationServiceImpl(@Qualifier("categoryConfigurationRepository") CategoryConfigurationRepository fixedAssetCategoryRepository) {
        this.categoryConfigurationRepository = fixedAssetCategoryRepository;
    }


    /**
     * @return Return all fixed assets categories
     */
    @Override
    public List<CategoryConfiguration> getAllCategoryConfigurations() {

        return categoryConfigurationRepository.findAll();
    }

    /**
     * @param fixedAssetCategory to be saved to fixedAssetCategory repository
     */
    @Override
    public void saveCategoryConfiguration(CategoryConfiguration fixedAssetCategory) {

        categoryConfigurationRepository.save(fixedAssetCategory);
    }

    @Cacheable("categoryConfigurationsById")
    @Override
    public CategoryConfiguration getCategoryConfigurationById(int id) {

        return categoryConfigurationRepository.findById(id).get();
    }

    @Cacheable("categoryConfigurationsByNames")
    @Override
    public CategoryConfiguration getCategoryByName(String categoryName) {

        return categoryConfigurationRepository.findFirstByDesignation(categoryName);
    }

    @Cacheable("depreciationRatesByCategoryNames")
    @Override
    public double getDepreciationRateByCategoryName(String categoryName) {

        return getCategoryByName(categoryName).getDepreciationRate();
    }
}
