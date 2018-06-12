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
package io.github.fasset.fasset.kernel.batch.depreciation.report.sol;

import io.github.fasset.fasset.model.depreciation.MonthlySolDepreciation;
import io.github.fasset.fasset.service.MonthlySolDepreciationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * Writes {@link MonthlySolDepreciation} to the database
 */
public class MonthlySolDepreciationWriter implements ItemWriter<MonthlySolDepreciation> {

    private static final Logger log = LoggerFactory.getLogger(MonthlySolDepreciationWriter.class);

    private final MonthlySolDepreciationService monthlySolDepreciationService;

    @Autowired
    public MonthlySolDepreciationWriter(@Qualifier("monthlySolDepreciationService") MonthlySolDepreciationService monthlySolDepreciationService) {
        this.monthlySolDepreciationService = monthlySolDepreciationService;
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
    public void write(List<? extends MonthlySolDepreciation> items) throws Exception {

        log.info("Saving a list of : {} MonthlySolDepreciation items to the Repository", items.size());

        try {
            monthlySolDepreciationService.saveAllMonthlyDepreciationItems(items);
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("MonthlySoLDepreciation items registered successfully...");
    }
}
