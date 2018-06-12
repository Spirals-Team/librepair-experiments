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

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.money.MonetaryAmount;

/**
 * DTO to carry the depreciation for each month of a year for each asset. Okay, a possible
 * performance issue is that there is a lot of boxing and un boxing going on, but this is
 * something that we will suffer because the database may not contain certain  months leading
 * to null items as opposed to zeroes. The only way to refactor this is to put tests on all batches
 * end to end, then see if they will still run without the auto boxing.
 * <p>
 * //TODO remove auto boxing
 */
public class MonthlyAssetDepreciationDTO {

    private static final Logger log = LoggerFactory.getLogger(MonthlyAssetDepreciationDTO.class);

    private Integer assetId;
    private Integer year;
    private Double jan;
    private Double feb;
    private Double mar;
    private Double apr;
    private Double may;
    private Double jun;
    private Double jul;
    private Double aug;
    private Double sep;
    private Double oct;
    private Double nov;
    private Double dec;

    public MonthlyAssetDepreciationDTO() {
    }

    public MonthlyAssetDepreciationDTO(Integer assetId, Integer year, MonetaryAmount jan, MonetaryAmount feb, MonetaryAmount mar, MonetaryAmount apr, MonetaryAmount may, MonetaryAmount jun,
                                       MonetaryAmount jul, MonetaryAmount aug, MonetaryAmount sep, MonetaryAmount oct, MonetaryAmount nov, MonetaryAmount dec) {
        this.assetId = assetId;
        this.year = year;
        this.jan = jan.getNumber().doubleValue();
        this.feb = feb.getNumber().doubleValue();
        this.mar = mar.getNumber().doubleValue();
        this.apr = apr.getNumber().doubleValue();
        this.may = may.getNumber().doubleValue();
        this.jun = jun.getNumber().doubleValue();
        this.jul = jul.getNumber().doubleValue();
        this.aug = aug.getNumber().doubleValue();
        this.sep = sep.getNumber().doubleValue();
        this.oct = oct.getNumber().doubleValue();
        this.nov = nov.getNumber().doubleValue();
        this.dec = dec.getNumber().doubleValue();
    }

    public Integer getAssetId() {
        return assetId;
    }

    public MonthlyAssetDepreciationDTO setAssetId(Integer assetId) {
        this.assetId = assetId;
        return this;
    }

    public Integer getYear() {

        log.trace("Returning year : {}", year);
        return year == null ? 0 : year;
    }

    public MonthlyAssetDepreciationDTO setYear(Integer year) {
        this.year = year;
        return this;
    }

    public Double getJan() {
        log.trace("Returning jan : {}", jan);
        return jan == null ? 0.00 : jan;
    }

    public MonthlyAssetDepreciationDTO setJan(Double jan) {
        this.jan = jan;
        return this;
    }

    public Double getFeb() {
        log.trace("Returning feb : {}", feb);
        return feb == null ? 0.00 : feb;
    }

    public MonthlyAssetDepreciationDTO setFeb(Double feb) {
        this.feb = feb;
        return this;
    }

    public Double getMar() {
        log.trace("Returning mar : {}", mar);
        return mar == null ? 0.00 : mar;
    }

    public MonthlyAssetDepreciationDTO setMar(Double mar) {
        this.mar = mar;
        return this;
    }

    public Double getApr() {
        log.trace("Returning apr : {}", apr);
        return apr == null ? 0.00 : apr;
    }

    public MonthlyAssetDepreciationDTO setApr(Double apr) {
        this.apr = apr;
        return this;
    }

    public Double getMay() {
        log.trace("Returning may : {}", may);
        return may == null ? 0.00 : may;
    }

    public MonthlyAssetDepreciationDTO setMay(Double may) {
        this.may = may;
        return this;
    }

    public Double getJun() {

        log.trace("Returning jun : {}", jun);
        return jun == null ? 0.00 : jun;
    }

    public MonthlyAssetDepreciationDTO setJun(Double jun) {
        this.jun = jun;
        return this;
    }

    public Double getJul() {
        log.trace("Returning jul : {}", jul);
        return jul == null ? 0.00 : jul;
    }

    public MonthlyAssetDepreciationDTO setJul(Double jul) {
        this.jul = jul;
        return this;
    }

    public Double getAug() {
        log.trace("Returning aug : {}", aug);
        return aug == null ? 0.00 : aug;
    }

    public MonthlyAssetDepreciationDTO setAug(Double aug) {
        this.aug = aug;
        return this;
    }

    public Double getSep() {
        log.trace("Returning sep : {}", sep);
        return sep == null ? 0.00 : sep;
    }

    public MonthlyAssetDepreciationDTO setSep(Double sep) {
        this.sep = sep;
        return this;
    }

    public Double getOct() {

        log.trace("Returning oct : {}", oct);
        return oct == null ? 0.00 : oct;
    }

    public MonthlyAssetDepreciationDTO setOct(Double oct) {
        this.oct = oct;
        return this;
    }

    public Double getNov() {
        log.trace("Returning nov : {}", nov);
        return nov == null ? 0.00 : nov;
    }

    public MonthlyAssetDepreciationDTO setNov(Double nov) {
        this.nov = nov;
        return this;
    }

    public Double getDec() {
        log.trace("Returning dec : {}", dec);
        return dec == null ? 0.00 : dec;
    }

    public MonthlyAssetDepreciationDTO setDec(Double dec) {
        this.dec = dec;
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
        MonthlyAssetDepreciationDTO that = (MonthlyAssetDepreciationDTO) o;
        return Objects.equal(assetId, that.assetId) && Objects.equal(year, that.year) && Objects.equal(jan, that.jan) && Objects.equal(feb, that.feb) && Objects.equal(mar, that.mar) &&
            Objects.equal(apr, that.apr) && Objects.equal(may, that.may) && Objects.equal(jun, that.jun) && Objects.equal(jul, that.jul) && Objects.equal(aug, that.aug) &&
            Objects.equal(sep, that.sep) && Objects.equal(oct, that.oct) && Objects.equal(nov, that.nov) && Objects.equal(dec, that.dec);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(assetId, year, jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("assetId", assetId).add("year", year).add("jan", jan).add("feb", feb).add("mar", mar).add("apr", apr).add("may", may).add("jun", jun)
            .add("jul", jul).add("aug", aug).add("sep", sep).add("oct", oct).add("nov", nov).add("dec", dec).toString();
    }
}
