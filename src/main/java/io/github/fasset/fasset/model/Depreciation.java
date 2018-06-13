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
import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.YearMonth;
import java.util.Objects;

/**
 * This is a record of a depreciation for a given asset for a given month
 *
 * @author edwin.njeru
 */
//@Audited Too expensive
@Entity(name = "Depreciation")
public class Depreciation extends DomainModel<String> {

    private static final Logger log = LoggerFactory.getLogger(Depreciation.class);

    @Column(name = "year")
    private int year;

    @Column(name = "month")
    private int month;

    @Column(name = "depreciation_period")
    private YearMonth depreciationPeriod;

    @Column(name = "fixed_asset_id")
    private int fixedAssetId;

    @Column(name = "category")
    private String category;

    @Column(name = "sol_id")
    private String solId;

    @Column
    @Type(type = "org.jadira.usertype.moneyandcurrency.moneta.PersistentMoneyAmount", parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "KES")})
    private Money depreciation;

    public Depreciation() {
    }

    public int getMonth() {
        return month;
    }

    public Depreciation setMonth(int month) {
        log.debug("Setting month for depreciationId : {}, as = {}", getId(), month);
        this.month = month;
        return this;
    }

    public int getYear() {
        return year;
    }

    public Depreciation setYear(int year) {
        log.debug("Setting year for depreciationId : {}, as = {}", getId(), depreciationPeriod);
        this.year = year;
        return this;
    }

    public YearMonth getDepreciationPeriod() {
        return depreciationPeriod;
    }

    public Depreciation setDepreciationPeriod(YearMonth depreciationPeriod) {

        log.debug("Setting depreciation period for depreciationId : {}, as = {}", getId(), depreciationPeriod);
        this.depreciationPeriod = depreciationPeriod;
        return this;
    }

    public int getFixedAssetId() {
        return fixedAssetId;
    }

    public Depreciation setFixedAssetId(int fixedAssetId) {

        log.debug("Setting fixedAssetId for depreciationId : {}, as = {}", getId(), fixedAssetId);
        this.fixedAssetId = fixedAssetId;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Depreciation setCategory(String category) {

        log.debug("Setting the category for depreciationId : {}, as = {}", getId(), category);
        this.category = category;
        return this;
    }

    public String getSolId() {
        return solId;
    }

    public Depreciation setSolId(String solId) {

        log.debug("Setting the sol nomenclature for depreciationId : {}, as ={}", getId(), solId);
        this.solId = solId;
        return this;
    }

    public Money getDepreciation() {
        return depreciation;
    }

    public Depreciation setDepreciation(Money depreciation) {

        log.debug("Setting the depreciation for depreciationId : {}, as = {}", getId(), depreciation);
        this.depreciation = depreciation;
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
        Depreciation that = (Depreciation) o;
        return fixedAssetId == that.fixedAssetId && Objects.equals(that.depreciation, depreciation) && Objects.equals(depreciationPeriod, that.depreciationPeriod) &&
            Objects.equals(category, that.category) && Objects.equals(solId, that.solId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), depreciationPeriod, fixedAssetId, category, solId, depreciation);
    }

    @Override
    public String toString() {
        return "Depreciation{" + "depreciationPeriod=" + depreciationPeriod + ", fixedAssetId=" + fixedAssetId + ", category='" + category + '\'' + ", solId='" + solId + '\'' + ", depreciation=" +
            depreciation + '}';
    }
}
