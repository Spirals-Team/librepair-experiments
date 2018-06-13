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

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.YearMonth;
import java.util.Objects;

//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"fixed_asset_id", "month", "sol_id", "category"})})

/**
 * This model represents a unit AccruedDepreciation in the repository
 */
@Audited
@Entity(name = "AccruedDepreciation")
public class AccruedDepreciation extends DomainModel<String> {

    @Column(name = "fixed_asset_id")
    private int fixedAssetId;

    @Column(name = "month")
    private YearMonth month;

    @Column(name = "sol_id")
    private String solId;

    @Column(name = "category")
    private String category;

    @Column
    @Type(type = "org.jadira.usertype.moneyandcurrency.moneta.PersistentMoneyAmount", parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "KES")})
    private Money accruedDepreciation;

    public int getFixedAssetId() {
        return fixedAssetId;
    }

    public AccruedDepreciation setFixedAssetId(int fixedAssetId) {
        this.fixedAssetId = fixedAssetId;
        return this;
    }

    public YearMonth getMonth() {
        return month;
    }

    public AccruedDepreciation setMonth(YearMonth month) {
        this.month = month;
        return this;
    }

    public String getSolId() {
        return solId;
    }

    public AccruedDepreciation setSolId(String solId) {
        this.solId = solId;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public AccruedDepreciation setCategory(String category) {
        this.category = category;
        return this;
    }

    public Money getAccruedDepreciation() {
        return accruedDepreciation;
    }

    public AccruedDepreciation setAccruedDepreciation(Money accruedDepreciation) {
        this.accruedDepreciation = accruedDepreciation;
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
        AccruedDepreciation that = (AccruedDepreciation) o;
        return fixedAssetId == that.fixedAssetId && Objects.equals(that.accruedDepreciation, accruedDepreciation) && Objects.equals(month, that.month) && Objects.equals(solId, that.solId) &&
            Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fixedAssetId, month, solId, category, accruedDepreciation);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AccruedDepreciation{");
        sb.append("fixedAssetId=").append(fixedAssetId);
        sb.append(", month=").append(month);
        sb.append(", solId='").append(solId).append('\'');
        sb.append(", category='").append(category).append('\'');
        sb.append(", accruedDepreciation=").append(accruedDepreciation);
        sb.append('}');
        return sb.toString();
    }
}
