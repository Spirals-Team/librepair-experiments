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
package io.github.fasset.fasset.repository;

import io.github.fasset.fasset.model.AccruedDepreciation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;

/**
 * This Repository extends the Spring JPA Template and has runtime-implentation depending on the
 * nature of the {@code Entity}
 */
@Repository("accruedDepreciationRepository")
public interface AccruedDepreciationRepository extends JpaRepository<AccruedDepreciation, Integer> {

    /**
     * Will return the AccruedDepreciation for a given fixedAssetId and the month before the month given
     *
     * @param fixedAssetId Id of the fixed asset being depreciation
     * @param month        Month period of depreciation
     * @return AccruedDepreciation
     */
    AccruedDepreciation findByFixedAssetIdAndMonthBefore(int fixedAssetId, YearMonth month);

}
