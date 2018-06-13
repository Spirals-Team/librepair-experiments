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
package io.github.fasset.fasset.kernel.excel.mapping;

import io.github.fasset.fasset.config.MapperOptions;

import java.util.List;

/**
 * This object reads connect from an excel file and maps the same connect to a list of the parameter DTO
 *
 * @author - Edwin Njeru
 */
public interface ExcelMapper {

    /**
     * This return a list of all items as they are read from the library
     *
     * @param c    class defining the mapped object
     * @param path path of the excel file
     * @return list of mapped objects
     */
    List mappedList(Class<?> c, String path);

    /**
     * @param mapperOptions for excel poiji reader
     */
    void setMapperOptions(MapperOptions mapperOptions);
}
