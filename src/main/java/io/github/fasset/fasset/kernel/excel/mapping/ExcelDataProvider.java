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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This is a delegate for generating a {@link List} of {@code T}
 * Given a filePath and a class it can generate a list of the objects of the class when
 * class and the excel file resonate with each other
 *
 * @param <T> Type of objects in a collection as read from the excel file
 */
@Component("excelDataProvider")
public class ExcelDataProvider<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelDataProvider.class);

    private ExcelMapper excelMapper;


    @Autowired
    public ExcelDataProvider(@Qualifier("excelMapper") ExcelMapper excelMapper) {
        this.excelMapper = excelMapper;
    }

    /**
     * Generates a {@link List} of the given type when needed from an implementation using only
     * the known path which is provided at runtime
     *
     * @param filePath where the excel file is temporarily stored
     * @param clazz    class into which we map the excel row
     * @return {@link List} of type {@code T}
     */
    public List<T> generateMappedList(String filePath, Class<T> clazz) {

        LOGGER.info("Generating a List of mapped objects of type : {} from file path :{}", clazz, filePath);

        List<T> dtoList = new LinkedList<>();

        if (filePath != null) {

            try {
                dtoList.addAll(excelMapper.mappedList(clazz, filePath));
            } catch (Throwable e) {
                String message = String.format("Unable to read from excel file : %s for class : %S", filePath, clazz);

                throw new ExcelMapperException(message, e);
            }

            LOGGER.info("Returning generated list of : {} items generated of type : {} from file path :{}", dtoList.size(), clazz, filePath);
        } else {

            LOGGER.error("The filePath given is null...");
        }

        return Collections.unmodifiableList(dtoList);
    }
}
