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
package io.github.fasset.fasset.model.depreciation;


import io.github.fasset.fasset.DomainModel;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;

/**
 * This object represents a record of an SOL's depreciation for all the months of
 * a year
 */
@Audited
@Entity
public class MonthlySolDepreciation extends DomainModel<String> {

    private String solId;
    private int year;
    private double jan;
    private double feb;
    private double mar;
    private double apr;
    private double may;
    private double jun;
    private double jul;
    private double aug;
    private double sep;
    private double oct;
    private double nov;
    private double dec;

    public MonthlySolDepreciation() {
    }

    public MonthlySolDepreciation(String solId, int year, double jan, double feb, double mar, double apr, double may, double jun, double jul, double aug, double sep, double oct, double nov,
                                  double dec) {
        this.solId = solId;
        this.year = year;
        this.jan = jan;
        this.feb = feb;
        this.mar = mar;
        this.apr = apr;
        this.may = may;
        this.jun = jun;
        this.jul = jul;
        this.aug = aug;
        this.sep = sep;
        this.oct = oct;
        this.nov = nov;
        this.dec = dec;
    }

    public String getSolId() {
        return solId;
    }

    public MonthlySolDepreciation setSolId(String solId) {
        this.solId = solId;
        return this;
    }

    public int getYear() {
        return year;
    }

    public MonthlySolDepreciation setYear(int year) {
        this.year = year;
        return this;
    }

    public double getJan() {
        return jan;
    }

    public MonthlySolDepreciation setJan(double jan) {
        this.jan = jan;
        return this;
    }

    public double getFeb() {
        return feb;
    }

    public MonthlySolDepreciation setFeb(double feb) {
        this.feb = feb;
        return this;
    }

    public double getMar() {
        return mar;
    }

    public MonthlySolDepreciation setMar(double mar) {
        this.mar = mar;
        return this;
    }

    public double getApr() {
        return apr;
    }

    public MonthlySolDepreciation setApr(double apr) {
        this.apr = apr;
        return this;
    }

    public double getMay() {
        return may;
    }

    public MonthlySolDepreciation setMay(double may) {
        this.may = may;
        return this;
    }

    public double getJun() {
        return jun;
    }

    public MonthlySolDepreciation setJun(double jun) {
        this.jun = jun;
        return this;
    }

    public double getJul() {
        return jul;
    }

    public MonthlySolDepreciation setJul(double jul) {
        this.jul = jul;
        return this;
    }

    public double getAug() {
        return aug;
    }

    public MonthlySolDepreciation setAug(double aug) {
        this.aug = aug;
        return this;
    }

    public double getSep() {
        return sep;
    }

    public MonthlySolDepreciation setSep(double sep) {
        this.sep = sep;
        return this;
    }

    public double getOct() {
        return oct;
    }

    public MonthlySolDepreciation setOct(double oct) {
        this.oct = oct;
        return this;
    }

    public double getNov() {
        return nov;
    }

    public MonthlySolDepreciation setNov(double nov) {
        this.nov = nov;
        return this;
    }

    public double getDec() {
        return dec;
    }

    public MonthlySolDepreciation setDec(double dec) {
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
        if (!super.equals(o)) {
            return false;
        }
        MonthlySolDepreciation that = (MonthlySolDepreciation) o;
        return year == that.year && Double.compare(that.jan, jan) == 0 && Double.compare(that.feb, feb) == 0 && Double.compare(that.mar, mar) == 0 && Double.compare(that.apr, apr) == 0 &&
            Double.compare(that.may, may) == 0 && Double.compare(that.jun, jun) == 0 && Double.compare(that.jul, jul) == 0 && Double.compare(that.aug, aug) == 0 &&
            Double.compare(that.sep, sep) == 0 && Double.compare(that.oct, oct) == 0 && Double.compare(that.nov, nov) == 0 && Double.compare(that.dec, dec) == 0 &&
            com.google.common.base.Objects.equal(solId, that.solId);
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(super.hashCode(), solId, year, jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec);
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        final StringBuilder sb = new StringBuilder("MonthlySolDepreciation{");
        sb.append("solId=").append(solId);
        sb.append(", year=").append(year);
        sb.append(", jan=").append(jan);
        sb.append(", feb=").append(feb);
        sb.append(", mar=").append(mar);
        sb.append(", apr=").append(apr);
        sb.append(", may=").append(may);
        sb.append(", jun=").append(jun);
        sb.append(", jul=").append(jul);
        sb.append(", aug=").append(aug);
        sb.append(", sep=").append(sep);
        sb.append(", oct=").append(oct);
        sb.append(", nov=").append(nov);
        sb.append(", dec=").append(dec);
        sb.append('}');
        return sb.toString();
    }
}
