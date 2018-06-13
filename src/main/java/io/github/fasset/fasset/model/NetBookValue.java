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
package io.github.fasset.fasset.model;

import io.github.fasset.fasset.DomainModel;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.YearMonth;
import java.util.Objects;

//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"fixed_asset_id", "month", "sol_id", "category"})})

/**
 * Record of the net book value for a gived fixed asset at a given month after previous month's depreciation
 *
 * @author edwin.njeru
 */
@Entity(name = "NetBookValue")
@Audited
public class NetBookValue extends DomainModel<String> {

    private static final Logger log = LoggerFactory.getLogger(NetBookValue.class);

    @Column(name = "fixed_asset_id")
    private int fixedAssetId;

    @Column(name = "month")
    private YearMonth month;

    @Column
    @Type(type = "org.jadira.usertype.moneyandcurrency.moneta.PersistentMoneyAmount", parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "KES")})
    private Money netBookValue;

    @Column(name = "sol_id")
    private String solId;

    @Column(name = "category")
    private String category;

    public NetBookValue() {
    }

    public NetBookValue(int fixedAssetId, YearMonth month, Money netBookValue, String solId, String category) {
        this.fixedAssetId = fixedAssetId;
        this.month = month;
        this.netBookValue = netBookValue;
        this.solId = solId;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public NetBookValue setCategory(String category) {
        log.trace("Setting fixedAssetId for NetBookValueId : {}, as = {}", getId(), fixedAssetId);
        this.category = category;
        return this;
    }

    public int getFixedAssetId() {
        return fixedAssetId;
    }

    public NetBookValue setFixedAssetId(int fixedAssetId) {
        log.trace("Setting fixedAssetId for NetBookValueId : {}, as = {}", getId(), fixedAssetId);
        this.fixedAssetId = fixedAssetId;
        return this;
    }

    public YearMonth getMonth() {
        return month;
    }

    public NetBookValue setMonth(YearMonth month) {
        log.trace("Setting month for NetBookValueId : {}, as = {}", getId(), month);
        this.month = month;
        return this;
    }

    public Money getNetBookValue() {
        return netBookValue;
    }

    public NetBookValue setNetBookValue(Money netBookValue) {

        log.trace("Setting NetBookValue for NetBookValueId : {} as = {}", getId(), netBookValue);
        this.netBookValue = netBookValue;
        return this;
    }

    public String getSolId() {
        return solId;
    }

    public NetBookValue setSolId(String solId) {

        log.trace("Setting SolId for NetBookValueId : {} as = {}", getId(), solId);
        this.solId = solId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        NetBookValue that = (NetBookValue) o;
        return fixedAssetId == that.fixedAssetId && Objects.equals(that.netBookValue, netBookValue) && Objects.equals(month, that.month) && Objects.equals(solId, that.solId) &&
            Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fixedAssetId, month, netBookValue, solId, category);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NetBookValue{");
        sb.append("fixedAssetId=").append(fixedAssetId);
        sb.append(", month=").append(month);
        sb.append(", netBookValue=").append(netBookValue);
        sb.append(", solId='").append(solId).append('\'');
        sb.append(", category='").append(category).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
