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

import io.github.fasset.fasset.DomainModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.Objects;

/**
 * A record of a depreciation job that is to be saved in the database
 */
@Entity(name = "DepreciationJobInstance")
public class DepreciationJobInstance extends DomainModel<String> implements Comparable<DepreciationJobInstance> {

    @Column(name = "month")
    private YearMonth month;

    public DepreciationJobInstance() {
    }

    public DepreciationJobInstance(YearMonth month) {
        this.month = month;
    }

    public YearMonth getMonth() {
        return month;
    }

    public void setMonth(YearMonth month) {
        this.month = month;
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
        DepreciationJobInstance that = (DepreciationJobInstance) o;
        return Objects.equals(month, that.month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), month);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DepreciationJobInstance{");
        sb.append("month=").append(month);
        sb.append('}');
        return sb.toString();
    }


    @Override
    public int compareTo(DepreciationJobInstance o) {
        return Comparator.comparing(DepreciationJobInstance::getMonth).compare(this, o);
    }
}
