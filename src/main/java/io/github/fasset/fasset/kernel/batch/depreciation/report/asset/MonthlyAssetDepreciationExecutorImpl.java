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

import io.github.fasset.fasset.kernel.batch.depreciation.model.MonthlyAssetDepreciationDTO;
import io.github.fasset.fasset.kernel.batch.depreciation.model.NilMonthlyAssetDepreciationDTO;
import io.github.fasset.fasset.model.FixedAsset;
import io.github.fasset.fasset.model.depreciation.MonthlyAssetDepreciation;
import io.github.fasset.fasset.repository.DepreciationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Executes generation of the MonthlyAssetDepreciation summary items
 */
@Service("monthlyAssetDepreciationExecutor")
@Transactional
public class MonthlyAssetDepreciationExecutorImpl implements MonthlyAssetDepreciationExecutor {

    private static final Logger log = LoggerFactory.getLogger(MonthlyAssetDepreciationExecutorImpl.class);

    private final DepreciationRepository depreciationRepository;

    @Autowired
    public MonthlyAssetDepreciationExecutorImpl(@Qualifier("depreciationRepository") DepreciationRepository depreciationRepository) {
        this.depreciationRepository = depreciationRepository;
    }

    /**
     * Returns {@link MonthlyAssetDepreciation} item that is updated with data from the depreciation
     * item
     *
     * @param fixedAsset {@link FixedAsset} for which we require the monthly Depreciation for a given year
     * @param year       for which we are getting monthly depreciation
     * @return MonthlyAssetDepreciation
     */
    @Override
    public MonthlyAssetDepreciation getMonthlyDepreciation(FixedAsset fixedAsset, Integer year) {

        MonthlyAssetDepreciationDTO dto = null;

        try {
            List<MonthlyAssetDepreciationDTO> tempList = depreciationRepository.getMonthlyAssetDepreciation(fixedAsset.getId(), year);
            MonthlyAssetDepreciationDTO temp;

            if (!tempList.isEmpty()) {
                temp = tempList.get(0);
            } else {
                log.debug("Returning nilMonthlyDepreciationDTO as there was no result from the depreciationRepository");
                temp = new NilMonthlyAssetDepreciationDTO(fixedAsset.getId(), year);
            }

            dto = temp;
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert dto != null;
        return new MonthlyAssetDepreciation(dto.getAssetId(), dto.getYear(), dto.getJan(), dto.getFeb(), dto.getMar(), dto.getApr(), dto.getMay(), dto.getJun(), dto.getJul(), dto.getAug(),
            dto.getSep(), dto.getOct(), dto.getNov(), dto.getDec());
    }
}
