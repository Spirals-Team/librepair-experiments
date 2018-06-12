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

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.YearMonth;

/**
 * Configuration for period from when depreciation starts and when depreciation ends for the initial
 * data uploads
 */
@ConfigurationProperties("depreciation")
public class DepreciationProperties {

    private YearMonth startMonth = YearMonth.of(2018, 1);
    private YearMonth stopMonth = YearMonth.of(2022, 12);

    public YearMonth getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(YearMonth startMonth) {
        this.startMonth = startMonth;
    }

    public YearMonth getStopMonth() {
        return stopMonth;
    }

    public void setStopMonth(YearMonth stopMonth) {
        this.stopMonth = stopMonth;
    }
}
