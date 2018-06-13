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

import io.github.fasset.fasset.kernel.batch.depreciation.model.MonthlyCategoryDepreciationDTO;
import io.github.fasset.fasset.model.depreciation.MonthlyCategoryDepreciation;
import io.github.fasset.fasset.repository.DepreciationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Executes the process of summarising depreciation for categories' depreciation on a month by month basis
 */
@Service("monthlyCategoryDepreciationExecutor")
public class MonthlyCategoryDepreciationExecutorImpl implements MonthlyCategoryDepreciationExecutor {

    private static final Logger log = LoggerFactory.getLogger(MonthlyCategoryDepreciationExecutorImpl.class);

    private final DepreciationRepository depreciationRepository;


    @Autowired
    public MonthlyCategoryDepreciationExecutorImpl(@Qualifier("depreciationRepository") DepreciationRepository depreciationRepository) {
        this.depreciationRepository = depreciationRepository;
    }

    /**
     * @param categoryName Name of the category we wish to summarise
     * @param year         Year of the depreciation
     * @return {@link MonthlyCategoryDepreciation } item relevant to the categoryName given
     * and the year
     */
    @Override
    public MonthlyCategoryDepreciation getMonthlyDepreciation(String categoryName, Integer year) {

        log.info("Pooling monthlyCategoryDepreciation in relation to category : {} for the year : {}", categoryName, year);

        MonthlyCategoryDepreciationDTO dto = depreciationRepository.getMonthlyCategoryDepreciation(categoryName, year).get(0);

        return new MonthlyCategoryDepreciation(dto.getCategoryName(), dto.getYear(), dto.getJan(), dto.getFeb(), dto.getMar(), dto.getApr(), dto.getMay(), dto.getJun(), dto.getJul(), dto.getAug(),
            dto.getSep(), dto.getOct(), dto.getNov(), dto.getDec());
    }
}
