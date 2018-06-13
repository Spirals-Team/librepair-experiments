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
package io.github.fasset.fasset.dto;

import io.github.fasset.fasset.model.brief.ServiceOutletBrief;

/**
 * This is a DTO for {@link ServiceOutletBrief} items containing simpler data types for viewing on the
 * front end
 */
public class ServiceOutletBriefResponseDto {

    private int id;

    /* nomenclature of the service outlet*/
    private String designation;

    /* total costs of all items in this category*/
    private double purchaseCost;

    /* total NBVs of all items in this category*/
    private double netBookValue;

    /* Total accumulated depreciaton for items in this category*/
    private double accruedDepreciation;

    /* total no. of items in this category*/
    private int poll;


    public ServiceOutletBriefResponseDto(ServiceOutletBrief serviceOutletBrief) {
        this.id = serviceOutletBrief.getId();
        this.designation = serviceOutletBrief.getDesignation();
        this.purchaseCost = serviceOutletBrief.getPurchaseCost().getNumber().doubleValue();
        this.netBookValue = serviceOutletBrief.getNetBookValue().getNumber().doubleValue();
        this.accruedDepreciation = serviceOutletBrief.getAccruedDepreciation().getNumber().doubleValue();
        this.poll = serviceOutletBrief.getPoll();
    }

    public int getId() {
        return id;
    }

    public ServiceOutletBriefResponseDto setId(int id) {
        this.id = id;
        return this;
    }

    public String getDesignation() {
        return designation;
    }

    public ServiceOutletBriefResponseDto setDesignation(String designation) {
        this.designation = designation;
        return this;
    }

    public double getPurchaseCost() {
        return purchaseCost;
    }

    public ServiceOutletBriefResponseDto setPurchaseCost(double purchaseCost) {
        this.purchaseCost = purchaseCost;
        return this;
    }

    public double getNetBookValue() {
        return netBookValue;
    }

    public ServiceOutletBriefResponseDto setNetBookValue(double netBookValue) {
        this.netBookValue = netBookValue;
        return this;
    }

    public double getAccruedDepreciation() {
        return accruedDepreciation;
    }

    public ServiceOutletBriefResponseDto setAccruedDepreciation(double accruedDepreciation) {
        this.accruedDepreciation = accruedDepreciation;
        return this;
    }

    public int getPoll() {
        return poll;
    }

    public ServiceOutletBriefResponseDto setPoll(int poll) {
        this.poll = poll;
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

        ServiceOutletBriefResponseDto that = (ServiceOutletBriefResponseDto) o;

        if (id != that.id) {
            return false;
        }
        if (Double.compare(that.purchaseCost, purchaseCost) != 0) {
            return false;
        }
        if (Double.compare(that.netBookValue, netBookValue) != 0) {
            return false;
        }
        if (Double.compare(that.accruedDepreciation, accruedDepreciation) != 0) {
            return false;
        }
        if (poll != that.poll) {
            return false;
        }
        return designation != null ? designation.equals(that.designation) : that.designation == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (designation != null ? designation.hashCode() : 0);
        temp = Double.doubleToLongBits(purchaseCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(netBookValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(accruedDepreciation);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + poll;
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ServiceOutletBriefResponseDto{");
        sb.append("nomenclature=").append(id);
        sb.append(", designation='").append(designation).append('\'');
        sb.append(", purchaseCost=").append(purchaseCost);
        sb.append(", netBookValue=").append(netBookValue);
        sb.append(", accruedDepreciation=").append(accruedDepreciation);
        sb.append(", poll=").append(poll);
        sb.append('}');
        return sb.toString();
    }
}
