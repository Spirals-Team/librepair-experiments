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
package io.github.fasset.fasset.kernel.batch.depreciation;

import io.github.fasset.fasset.model.AccruedDepreciation;
import io.github.fasset.fasset.model.FixedAsset;

import java.time.YearMonth;
import java.util.Objects;

/**
 * Default AccruedDepreciation for an asset if depreciation does not occur in a given month
 */
public class UnModifiedAccruedDepreciation {

    private AccruedDepreciation accruedDepreciation;

    public UnModifiedAccruedDepreciation(FixedAsset asset, YearMonth month) {

        accruedDepreciation =
            new AccruedDepreciation().setAccruedDepreciation(asset.getPurchaseCost().subtract(asset.getNetBookValue())).setCategory(asset.getCategory()).setFixedAssetId(asset.getId()).setMonth(month)
                .setSolId(asset.getSolId());
    }

    public AccruedDepreciation getAccruedDepreciation() {
        return accruedDepreciation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UnModifiedAccruedDepreciation that = (UnModifiedAccruedDepreciation) o;
        return Objects.equals(accruedDepreciation, that.accruedDepreciation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accruedDepreciation);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UnModifiedAccruedDepreciation{");
        sb.append("accruedDepreciation=").append(accruedDepreciation);
        sb.append('}');
        return sb.toString();
    }
}
