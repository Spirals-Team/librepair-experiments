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
package io.github.fasset.fasset.kernel.batch.depreciation.report.category;

import io.github.fasset.fasset.model.depreciation.MonthlyCategoryDepreciation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Objects;

/**
 * Processes data for a given year generating a MonthlyCategoryDepreciation for the same year
 */
public class MonthlyCategoryDepreciationProcessor implements ItemProcessor<String, MonthlyCategoryDepreciation> {

    private static final Logger log = LoggerFactory.getLogger(MonthlyCategoryDepreciationProcessor.class);
    private final MonthlyCategoryDepreciationExecutor executor;
    private String year;

    @Autowired// Check if dependencies are autowired correctly
    public MonthlyCategoryDepreciationProcessor(@Qualifier("monthlyCategoryDepreciationExecutor") MonthlyCategoryDepreciationExecutor executor, String year) {
        this.executor = executor;
        this.year = year;
    }

    /**
     * Process the provided item, returning a potentially modified or new item for continued
     * processing.  If the returned result is null, it is assumed that processing of the item
     * should not continue.
     *
     * @param item to be processed
     * @return potentially modified or new item for continued processing, null if processing of the
     * provided item should not continue.
     * @throws Exception thrown if exception occurs during processing.
     */
    @Override
    public MonthlyCategoryDepreciation process(String item) throws Exception {

        return executor.getMonthlyDepreciation(item, Integer.parseInt(Objects.requireNonNull(year)));
    }
}
