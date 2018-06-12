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
package io.github.fasset.fasset.kernel.batch.upload;

import io.github.fasset.fasset.kernel.util.ConverterException;
import io.github.fasset.fasset.model.AccruedDepreciation;
import io.github.fasset.fasset.model.FixedAsset;
import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.YearMonth;

/**
 * Reads data from a collection of fixedAssets and creates accruedDepreciation items out of it
 */
@Component("fixedAssetAccruedDepreciationProcessor")
public class FixedAssetAccruedDepreciationProcessor implements ItemProcessor<FixedAsset, AccruedDepreciation> {

    private static final Logger log = LoggerFactory.getLogger(FixedAssetAccruedDepreciationProcessor.class);

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
    public AccruedDepreciation process(FixedAsset fixedAsset) throws Exception {

        log.debug("Processing fixedAsset item : {}", fixedAsset);

        AccruedDepreciation retVal = new AccruedDepreciation();

        Money acc = fixedAsset.getPurchaseCost().subtract(fixedAsset.getNetBookValue());

        try {
            retVal.setCategory(fixedAsset.getCategory()).setFixedAssetId(fixedAsset.getId()).setSolId(fixedAsset.getSolId()).setMonth(YearMonth.of(2017, 12))//TODO configure to do this from controller
                .setAccruedDepreciation(acc);
        } catch (Throwable e) {
            String message = String.format("Exception encountered while deriving accruedDepreciation from" + "%S", fixedAsset);

            throw new ConverterException(message, e);
        }

        return retVal;
    }
}
