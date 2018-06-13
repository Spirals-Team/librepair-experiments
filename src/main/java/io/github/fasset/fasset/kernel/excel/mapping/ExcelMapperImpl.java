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

import com.poiji.bind.Poiji;
import io.github.fasset.fasset.config.MapperOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Thread-safe implementation of the {@link ExcelMapper}
 * The client must specify the parameter type
 *
 * @author edwin.njeru
 */
@Component("excelMapper")
public class ExcelMapperImpl implements ExcelMapper {

    private static final Logger log = LoggerFactory.getLogger(ExcelMapperImpl.class);

    private MapperOptions mapperOptions;


    @Autowired
    public ExcelMapperImpl(@Qualifier("mapperOptions") MapperOptions mapperOptions) {
        this.mapperOptions = mapperOptions;
    }

    @Override
    public void setMapperOptions(MapperOptions mapperOptions) {
        this.mapperOptions = mapperOptions;
    }

    /**
     * This return a list of all items as they are read from the excel document in the file system.
     *
     * @param clazz class defining the mapped object
     * @param path  path of the excel file
     * @return list of mapped objects
     */
    @SuppressWarnings("unchecked")
    @Override
    public List mappedList(Class<?> clazz, String path) {

        log.info("Creating a mapped list for class : {}, using file from the path : {}", clazz, path);

        List mappedlist = new LinkedList<>();

        try {

            mappedlist.addAll(Poiji.fromExcel(new File(path), clazz, mapperOptions.getPoijiOptions()));

        } catch (Throwable e) {

            String message = String.format("Unable to read from excel file : %s for class : %S with options : %s", path, clazz, mapperOptions);

            throw new ExcelMapperException(message, e);
        }

        log.info("{} items have been inserted into a list...", mappedlist.size());

        return Collections.unmodifiableList(mappedlist);
    }

}
