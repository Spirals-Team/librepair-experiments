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

import io.github.fasset.fasset.model.AccruedDepreciation;
import io.github.fasset.fasset.service.AccruedDepreciationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Writes AccruedDepreciation items to the database
 */
@Component("accruedDepreciationWriter")
public class AccruedDepreciationWriter implements ItemWriter<AccruedDepreciation> {

    private static final Logger log = LoggerFactory.getLogger(AccruedDepreciationWriter.class);

    private final AccruedDepreciationService accruedDepreciationService;

    @Autowired
    public AccruedDepreciationWriter(@Qualifier("accruedDepreciationService") AccruedDepreciationService accruedDepreciationService) {
        this.accruedDepreciationService = accruedDepreciationService;
    }


    /**
     * Process the supplied data element. Will not be called with any null items
     * in normal operation.
     *
     * @param items items to be written
     * @throws Exception if there are errors. The framework will catch the
     *                   exception and convert or rethrow it as appropriate.
     */
    @Override
    public void write(List<? extends AccruedDepreciation> items) throws Exception {

        log.info("Wring a chunk of {} items to repository", items.size());

        accruedDepreciationService.saveAllAccruedDepreciationRecords(items);
    }
}
