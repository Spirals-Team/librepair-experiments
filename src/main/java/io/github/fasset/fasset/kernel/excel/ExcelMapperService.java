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
package io.github.fasset.fasset.kernel.excel;

import io.github.fasset.fasset.model.FixedAssetDTO;

import java.util.List;

/**
 * This interface generates collection of data from an excel file given the fileName
 */
public interface ExcelMapperService {

    /**
     * @param fileName where the excel file is located
     * @return {@link List} of {@link FixedAssetDTO} items from the fileName
     */
    List<FixedAssetDTO> fetchExcelData(String fileName);
}
