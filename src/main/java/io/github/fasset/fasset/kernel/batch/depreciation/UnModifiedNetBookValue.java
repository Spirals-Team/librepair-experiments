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

import io.github.fasset.fasset.model.FixedAsset;
import io.github.fasset.fasset.model.NetBookValue;

import java.time.YearMonth;
import java.util.Objects;

/**
 * Default value of NetBookValue for an asset if depreciation does not occur in a given month
 */
public class UnModifiedNetBookValue {

    private NetBookValue netBookValue;


    public UnModifiedNetBookValue(FixedAsset asset, YearMonth month) {

        netBookValue = new NetBookValue();

        netBookValue.setCategory(asset.getCategory()).setNetBookValue(asset.getNetBookValue()).setMonth(month).setSolId(asset.getSolId()).setFixedAssetId(asset.getId());
    }

    public NetBookValue getNetBookValue() {
        return netBookValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UnModifiedNetBookValue that = (UnModifiedNetBookValue) o;
        return Objects.equals(netBookValue, that.netBookValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(netBookValue);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UnModifiedNetBookValue{");
        sb.append("netBookValue=").append(netBookValue);
        sb.append('}');
        return sb.toString();
    }
}
