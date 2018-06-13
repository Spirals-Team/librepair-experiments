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
package io.github.fasset.fasset.config;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Thin wrapper class for options needed by {@link Poiji} library to read excel file
 *
 * @author - Edwin Njeru
 */
@Component("mapperOptions")
@PropertySource("classpath:excel-mapping.properties")
public class MapperOptions {

    @Value("${excel.date.pattern}")
    private String datePattern;

    @Value("${excel.skip.rows}")
    private Integer skipRows;


    public MapperOptions(String datePattern, Integer skipRows) {
        this.datePattern = datePattern;
        this.skipRows = skipRows;
    }

    public MapperOptions() {
    }

    public PoijiOptions getPoijiOptions() {

        return PoijiOptionsBuilder.settings().datePattern(datePattern).skip(skipRows).build();
    }

    public String getDatePattern() {
        return datePattern;
    }

    public MapperOptions setDatePattern(String datePattern) {
        this.datePattern = datePattern;
        return this;
    }

    public Integer getSkipRows() {
        return skipRows;
    }

    public MapperOptions setSkipRows(Integer skipRows) {
        this.skipRows = skipRows;
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
        MapperOptions options = (MapperOptions) o;
        return Objects.equal(datePattern, options.datePattern) && Objects.equal(skipRows, options.skipRows);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(datePattern, skipRows);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("datePattern", datePattern).add("skipRows", skipRows).toString();
    }
}
