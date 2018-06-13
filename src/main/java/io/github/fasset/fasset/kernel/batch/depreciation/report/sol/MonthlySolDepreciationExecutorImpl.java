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


import io.github.fasset.fasset.kernel.batch.depreciation.model.MonthlySolDepreciationDTO;
import io.github.fasset.fasset.model.depreciation.MonthlySolDepreciation;
import io.github.fasset.fasset.repository.DepreciationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Implementation for the MonthlySolDepreciationExecutor. This implementation could be improved upon if we
 * reduce the trips to the depreciation repository, and cache all data into a list instead. But that
 * might involve changing the structure of the entire batch. Future performance enhancement might start
 * from implementation of this interface
 */
@Service("monthlySolDepreciationExecutor")
public class MonthlySolDepreciationExecutorImpl implements MonthlySolDepreciationExecutor {

    private static final Logger log = LoggerFactory.getLogger(MonthlySolDepreciationExecutorImpl.class);

    private final DepreciationRepository depreciationRepository;

    @Autowired
    public MonthlySolDepreciationExecutorImpl(@Qualifier("depreciationRepository") DepreciationRepository depreciationRepository) {
        this.depreciationRepository = depreciationRepository;
    }

    /**
     * Returns MonthlySolDepreciation item relative to the parameters of the year of depreciation
     * and the solId
     *
     * @param solId The service outlet Id
     * @param year  Year of depreciation
     * @return MonthlySolDepreciation
     */
    @Override
    public MonthlySolDepreciation getMonthlyDepreciation(String solId, Integer year) {

        log.info("Generating monthlySolDepreciation record relative to solId : {} and " + "for the year : {}", solId, year);

        MonthlySolDepreciationDTO dto = depreciationRepository.getMonthlySolDepreciation(solId, year).get(0);

        return new MonthlySolDepreciation(dto.getSolId(), dto.getYear(), dto.getJan(), dto.getFeb(), dto.getMar(), dto.getApr(), dto.getMay(), dto.getJun(), dto.getJul(), dto.getAug(), dto.getSep(),
            dto.getOct(), dto.getNov(), dto.getDec());
    }
}
