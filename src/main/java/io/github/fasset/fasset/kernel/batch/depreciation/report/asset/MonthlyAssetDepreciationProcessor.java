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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Objects;

/**
 * Processor for MonthlyAssetDepreciation creates a MonthlyAssetDepreciation object from a FixedAsset object
 */
public class MonthlyAssetDepreciationProcessor implements ItemProcessor<FixedAsset, MonthlyAssetDepreciation> {

    private static final Logger log = LoggerFactory.getLogger(MonthlyAssetDepreciationProcessor.class);
    private final MonthlyAssetDepreciationExecutor monthlyAssetDepreciationExecutor;
    private String year;

    public MonthlyAssetDepreciationProcessor(@Qualifier("monthlyAssetDepreciationExecutor") MonthlyAssetDepreciationExecutor monthlyAssetDepreciationExecutor, String year) {
        this.year = year;
        this.monthlyAssetDepreciationExecutor = monthlyAssetDepreciationExecutor;
    }

    /**
     * Process the provided item, returning a potentially modified or new item for continued
     * processing.  If the returned result is null, it is assumed that processing of the item
     * should not continue.
     *
     * @param fixedAsset to be processed
     * @return potentially modified or new item for continued processing, null if processing of the
     * provided item should not continue.
     * @throws Exception thrown if exception occurs during processing.
     */
    @Override
    public MonthlyAssetDepreciation process(FixedAsset fixedAsset) throws Exception {

        log.debug("Generating monthlyAssetDepreciation for fixedAsset nomenclature : {}", fixedAsset.getId());

        return monthlyAssetDepreciationExecutor.getMonthlyDepreciation(fixedAsset, Integer.parseInt(Objects.requireNonNull(year)));
    }
}
