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
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * This is a representative model of the fixed asset's category for purposes of depreciation, that is its
 * name, its depreciation rate and its depreciation logic
 */
@Entity(name = "CategoryConfiguration")
@Table(name = "category_configuration",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"designation", "depreciation_logic", "depreciation_logic", "deprecant", "depreciation_rate", "category_ledger_id"})})
@Audited
public class CategoryConfiguration extends DomainModel<String> {

    @NotNull(message = "Please provide a valid designation for category")
    @Column(name = "designation")
    private String designation;

    /**
     * The name of the depreciation logic
     */
    @NotNull(message = "Please provide a valid designation for depreciation logic")
    @Column(name = "depreciation_logic")
    private String depreciationLogic;

    /**
     * This is the item on which the depreciation rate is applied, as in either the cost
     * or the net book value
     */
    @NotNull(message = "Please provide a valid designation for depreciation deprecant")
    @Column(name = "deprecant")
    private String deprecant;

    @NotNull(message = "Please provide depreciation per annum")
    @Column(name = "depreciation_rate")
    private double depreciationRate;

    @NotNull(message = "Kindly supply the ledgerId for thiis category")
    @Column(name = "category_ledger_id")
    private String categoryLedgerId;

    public CategoryConfiguration(@NotNull(message = "Please provide a valid designation for category") String designation,
                                 @NotNull(message = "Please provide a valid designation for depreciation logic") String depreciationLogic,
                                 @NotNull(message = "Please provide a valid designation for depreciation deprecant") String deprecant,
                                 @NotNull(message = "Please provide depreciation per annum") double depreciationRate,
                                 @NotNull(message = "Kindly supply the ledgerId for thiis category") String categoryLedgerId) {
        this.designation = designation.toUpperCase();
        this.depreciationLogic = depreciationLogic.toUpperCase();
        this.deprecant = deprecant.toUpperCase();
        this.depreciationRate = depreciationRate;
        this.categoryLedgerId = categoryLedgerId;
    }

    public CategoryConfiguration() {
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation.toUpperCase();
    }

    public String getDepreciationLogic() {
        return depreciationLogic;
    }

    public void setDepreciationLogic(String depreciationLogic) {
        this.depreciationLogic = depreciationLogic.toUpperCase();
    }

    public String getDeprecant() {
        return deprecant;
    }

    public void setDeprecant(String deprecant) {
        this.deprecant = deprecant.toUpperCase();
    }

    public double getDepreciationRate() {
        return depreciationRate;
    }

    public void setDepreciationRate(double depreciationRate) {
        this.depreciationRate = depreciationRate;
    }

    public String getCategoryLedgerId() {
        return categoryLedgerId;
    }

    public void setCategoryLedgerId(String categoryLedgerId) {
        this.categoryLedgerId = categoryLedgerId;
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
        CategoryConfiguration that = (CategoryConfiguration) o;
        return Double.compare(that.depreciationRate, depreciationRate) == 0 && Objects.equals(designation, that.designation) && Objects.equals(depreciationLogic, that.depreciationLogic) &&
            Objects.equals(deprecant, that.deprecant) && Objects.equals(categoryLedgerId, that.categoryLedgerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), designation, depreciationLogic, deprecant, depreciationRate, categoryLedgerId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CategoryConfiguration{");
        sb.append("designation='").append(designation).append('\'');
        sb.append(", depreciationLogic='").append(depreciationLogic).append('\'');
        sb.append(", deprecant='").append(deprecant).append('\'');
        sb.append(", depreciationRate=").append(depreciationRate);
        sb.append(", categoryLedgerId='").append(categoryLedgerId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
