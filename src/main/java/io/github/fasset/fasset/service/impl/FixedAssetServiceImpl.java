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

import io.github.fasset.fasset.kernel.util.DataRetrievalFromServiceException;
import io.github.fasset.fasset.kernel.util.ImmutableListCollector;
import io.github.fasset.fasset.model.FixedAsset;
import io.github.fasset.fasset.model.brief.CategoryBrief;
import io.github.fasset.fasset.model.brief.ServiceOutletBrief;
import io.github.fasset.fasset.repository.FixedAssetRepository;
import io.github.fasset.fasset.service.FixedAssetService;
import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * {@link FixedAssetService} implementation
 */
@Service("fixedAssetService")
@Transactional
public class FixedAssetServiceImpl implements FixedAssetService {

    private static final Logger log = LoggerFactory.getLogger(FixedAssetServiceImpl.class);

    private final FixedAssetRepository fixedAssetRepository;

    @Autowired
    public FixedAssetServiceImpl(@Qualifier("fixedAssetRepository") FixedAssetRepository fixedAssetRepository) {
        this.fixedAssetRepository = fixedAssetRepository;
    }

    /**
     * Saves all {@link FixedAsset} items passed in a list, saving unique items only.
     * Quietly fails if the asset is already in the database
     *
     * @param fixedAssets Collection of fixedAsset entities to be saved in the database
     */
    @Override
    public void saveAllFixedAssets(List<? extends FixedAsset> fixedAssets) {

        fixedAssetRepository.saveAll(fixedAssets);
    }

    /**
     * Fetches a List of all existing items in the {@link FixedAssetRepository}
     *
     * @return Collection of fixedAsset entities extracted from the database
     */
    @Override
    public List<FixedAsset> fetchAllExistingAssets() {

        return fixedAssetRepository.findAll().parallelStream().sorted().collect(ImmutableListCollector.toImmutableList());
    }

    /**
     * Extracts the fixed asset when the nomenclature is known
     *
     * @param id of fixedAsset entity to be fetched from database
     * @return FixedAsset entity whose nomenclature was found in the database
     */
    @Override
    @Cacheable("fixedAssetsFetchedByIds")
    public FixedAsset fetchAssetGivenId(int id) {

        return fixedAssetRepository.findById(id).get();
    }

    /**
     * By querying the {@link FixedAssetRepository} this method
     * is able to create a {@link CategoryBrief} for the category given in the parameter
     *
     * @param category for which we are preparing a brief
     * @return {@link CategoryBrief}
     */
    @Override
    @Cacheable("categoryBriefsFetchedByCategoryNames")
    public CategoryBrief getCategoryBrief(String category) {
        CategoryBrief brief = new CategoryBrief();

        log.info("Preparing a brief for category : {}", category);

        brief.setDesignation(category);

        Money cost = fixedAssetRepository.getTotalCategoryPurchaseCost(category);
        log.info("Setting purchase cost as : {}", cost);
        brief.setPurchaseCost(cost);

        Money nbv = fixedAssetRepository.getTotalCategoryNetBookValue(category);
        brief.setNetBookValue(nbv);

        int count = fixedAssetRepository.getTotalCategoryCount(category);
        log.info("Setting poll as : {}", count);
        brief.setPoll(count);

        Money acc = brief.getPurchaseCost().subtract(brief.getNetBookValue());
        log.info("Setting accrued depreciation as : {}", acc);
        brief.setAccruedDepreciation(acc);

        /*try {
            //TODO include the methods here
        } catch (Throwable e) {
            String message = String.format("Exception encountered while creating a categoryBrief for category : %s", category);
            throw new DataRetrievalFromServiceException(message, e);
        }*/

        log.debug("Brief for category returned : {}", brief);

        return brief;
    }

    /**
     * By querying the {@link FixedAssetRepository} this method
     * is able to create a {@link ServiceOutletBrief} for the SOL queried in the parameter
     *
     * @param solId for which we are preparing a brief
     * @return {@link CategoryBrief}
     */
    @Override
    @Cacheable("serviceOutletBriefsFetchedBySolIds")
    public ServiceOutletBrief getServiceOutletBrief(String solId) {
        ServiceOutletBrief brief = new ServiceOutletBrief();

        log.info("Preparing a brief for solId : {}", solId);

        try {
            brief.setDesignation(solId);
            brief.setPurchaseCost(fixedAssetRepository.getTotalSolPurchaseCost(solId));
            brief.setNetBookValue(fixedAssetRepository.getTotalSolNetBookValue(solId));
            brief.setAccruedDepreciation(brief.getPurchaseCost().subtract(brief.getNetBookValue()));
            brief.setPoll(fixedAssetRepository.getTotalSolCount(solId));
        } catch (Throwable e) {
            String message = String.format("Exception encountered while creating a serviceOutletBrief for solId : %s", solId);
            throw new DataRetrievalFromServiceException(message, e);
        }

        log.debug("Brief for service outlet returned : {}", brief);

        return brief;
    }

    /**
     * @return A unique list of all solIds in the database
     */
    @Override
    public List<String> getAllSolIds() {

        return fixedAssetRepository.getDistinctSolIds();
    }

    /**
     * @return A unique list of all categories in the database
     */
    @Override
    public List<String> getAllCategories() {

        return fixedAssetRepository.getDistinctCategories();
    }

    /**
     * @param fixedAsset to be saved to fixedAssetRepository
     */
    @Override
    public FixedAsset saveFixedAsset(FixedAsset fixedAsset) {

        return fixedAssetRepository.save(fixedAsset);
    }

    /**
     * @return # of assets
     */
    @Override
    public int getPoll() {

        return Math.toIntExact(fixedAssetRepository.count());
    }
}
