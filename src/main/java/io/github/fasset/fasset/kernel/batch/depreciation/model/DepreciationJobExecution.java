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

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Objects;

/**
 * Model that represents a depreciation unit of work done that can be recorded in the
 * database
 */
@Entity(name = "DepreciationJobExecution")
public class DepreciationJobExecution extends DomainModel<String> {

    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private LocalDateTime lastUpdatedTime;
    private String status;
    private int noOfItems;
    private YearMonth period;

    public DepreciationJobExecution() {
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(LocalDateTime lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNoOfItems() {
        return noOfItems;
    }

    public void setNoOfItems(int noOfItems) {
        this.noOfItems = noOfItems;
    }

    public YearMonth getPeriod() {
        return period;
    }

    public void setPeriod(YearMonth period) {
        this.period = period;
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
        DepreciationJobExecution that = (DepreciationJobExecution) o;
        return noOfItems == that.noOfItems && Objects.equals(startTime, that.startTime) && Objects.equals(finishTime, that.finishTime) && Objects.equals(lastUpdatedTime, that.lastUpdatedTime) &&
            Objects.equals(status, that.status) && Objects.equals(period, that.period);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), startTime, finishTime, lastUpdatedTime, status, noOfItems, period);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DepreciaionJobExecution{");
        sb.append("startTime=").append(startTime);
        sb.append(", finishTime=").append(finishTime);
        sb.append(", lastUpdatedTime=").append(lastUpdatedTime);
        sb.append(", status='").append(status).append('\'');
        sb.append(", noOfItems=").append(noOfItems);
        sb.append(", period=").append(period);
        sb.append('}');
        return sb.toString();
    }
}
