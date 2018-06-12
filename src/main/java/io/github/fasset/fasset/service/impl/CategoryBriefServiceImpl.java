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

import io.github.fasset.fasset.kernel.util.DataRetrievalFromControllerException;
import io.github.fasset.fasset.model.brief.CategoryBrief;
import io.github.fasset.fasset.repository.CategoryBriefRepository;
import io.github.fasset.fasset.service.CategoryBriefService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link CategoryBriefService} implementation
 */
@Service("categoryBriefService")
public class CategoryBriefServiceImpl implements CategoryBriefService {

    private static final Logger log = LoggerFactory.getLogger(CategoryBriefServiceImpl.class);


    private final CategoryBriefRepository categoryBriefRepository;

    @Autowired
    public CategoryBriefServiceImpl(@Qualifier("categoryBriefRepository") CategoryBriefRepository categoryBriefRepository) {
        this.categoryBriefRepository = categoryBriefRepository;
    }

    /**
     * @return {@link List} of {@link CategoryBrief} items fetched from the repository
     */
    @Override
    public List<CategoryBrief> fetchAllCategoryBriefs() {

        List<CategoryBrief> categoryBriefs;

        log.info("Fetching with categoryBriefs from repo...");

        try {
            categoryBriefs = categoryBriefRepository.findAll();
        } catch (Throwable e) {
            String message = "Error fetching categoryBriefs from repository";
            throw new DataRetrievalFromControllerException(message, e);
        }

        log.info("Returning a list of : {} from the repository", categoryBriefs.size());

        return categoryBriefs;
    }

    /**
     * @param id of the CategoryBrief
     * @return {@link CategoryBrief} of the nomenclature given as param
     */
    @Cacheable("categoryBriefsFromIds")
    @Override
    public CategoryBrief fetchCategoryBriefGivenId(int id) {

        CategoryBrief categoryBrief = null;

        try {
            categoryBrief = categoryBriefRepository.findById(id).get();
        } catch (Throwable e) {
            String message = "Error fetching categoryBriefs from repository";
            throw new DataRetrievalFromControllerException(message, e);
        }

        log.debug("Returning category : {} from repository", categoryBrief);

        return categoryBrief;
    }

    /**
     * All existing items are updated while new ones are newly added to the repository...
     * To the ends that the designation remains unique
     *
     * @param categoryBriefs items to be saved to repository
     */
    @Override
    @Transactional
    public void saveAllCategoryBriefItems(List<? extends CategoryBrief> categoryBriefs) {

        List<CategoryBrief> unsavedItems = new ArrayList<>();

        for (CategoryBrief brief : categoryBriefs) {

            CategoryBrief persisted = categoryBriefRepository.findDistinctByDesignation(brief.getDesignation());

            if (persisted != null) {
                persisted.setDesignation(brief.getDesignation());
                persisted.setPoll(brief.getPoll());
                persisted.setPurchaseCost(brief.getPurchaseCost());
                persisted.setNetBookValue(brief.getNetBookValue());
                persisted.setAccruedDepreciation(brief.getAccruedDepreciation());
            } else {
                unsavedItems.add(brief);
            }
        }

        categoryBriefRepository.saveAll(unsavedItems);
    }
}
