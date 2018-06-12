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
package io.github.fasset.fasset.kernel.batch.depreciation.model;

/**
 * Default depreciation for each month of the year for a given asset
 */
public class NilMonthlyAssetDepreciationDTO extends MonthlyAssetDepreciationDTO {

    public NilMonthlyAssetDepreciationDTO(Integer assetId, Integer year) {
        super();
        super.setAssetId(assetId).setYear(year);
    }

    @Override
    public Integer getAssetId() {
        return 0;
    }

    @Override
    public Integer getYear() {
        return 0;
    }

    @Override
    public Double getJan() {
        return 0.00;
    }

    @Override
    public Double getFeb() {
        return 0.00;
    }

    @Override
    public Double getMar() {
        return 0.00;
    }

    @Override
    public Double getApr() {
        return 0.00;
    }

    @Override
    public Double getMay() {
        return 0.00;
    }

    @Override
    public Double getJun() {
        return 0.00;
    }

    @Override
    public Double getJul() {
        return 0.00;
    }

    @Override
    public Double getAug() {
        return 0.00;
    }

    @Override
    public Double getSep() {
        return 0.00;
    }

    @Override
    public Double getOct() {
        return 0.00;
    }

    @Override
    public Double getNov() {
        return 0.00;
    }

    @Override
    public Double getDec() {
        return 0.00;
    }

}
