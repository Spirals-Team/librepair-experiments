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
package io.github.fasset.fasset.model.nil;

import io.github.fasset.fasset.model.CategoryConfiguration;

/**
 * This object represents data improperly configured CategoryConfiguration through
 * the use of defaults
 */
public class NilCategoryConfiguration {

    private final String designation;

    /**
     * The name of the depreciation logic
     */
    private final String depreciationLogic;

    /**
     * This is the item on which the depreciation rate is applied, as in either the cost
     * or the net book value
     */
    private final String deprecant;

    private final double depreciationRate;

    private final String categoryLedgerId;

    public NilCategoryConfiguration() {
        this.designation = "Category";
        this.depreciationLogic = "DECLININGBALANCE";
        this.deprecant = "NETBOOKVALUE";
        this.depreciationRate = 0.00;
        this.categoryLedgerId = "0000000000";
    }

    public CategoryConfiguration getCategoryConfiguration() {
        return new CategoryConfiguration(designation, depreciationLogic, deprecant, depreciationRate, categoryLedgerId);
    }
}
