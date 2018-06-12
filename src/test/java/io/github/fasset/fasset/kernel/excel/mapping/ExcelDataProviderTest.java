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
import io.github.fasset.fasset.model.FixedAssetDTO;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class ExcelDataProviderTest {

    private ExcelDataProvider<FixedAssetDTO> excelDataProvider;

    private String testFilePath;

    @Before
    public void setUp() throws Exception {

        excelDataProvider = new ExcelDataProvider<>(new ExcelMapperImpl(new MapperOptions("MM/dd/yy", 1)));

        testFilePath = Objects.requireNonNull(getClass().getClassLoader().getResource("Data1.xlsx")).getPath();
    }

    @Test
    public void excelDataProvider() throws Exception {

        List<FixedAssetDTO> excelAssetList = excelDataProvider.generateMappedList(Objects.requireNonNull(testFilePath), FixedAssetDTO.class);

        DateFormat format = new SimpleDateFormat("MM/dd/yy");

        assertEquals(302, excelAssetList.size());
        assertEquals("ABC0001842", excelAssetList.get(0).getBarcode());
        assertEquals("005", excelAssetList.get(0).getSolId());
        assertEquals("PRINTER EPSON LX300", excelAssetList.get(0).getAssetDescription());
        assertEquals(format.parse("28/10/1991"), excelAssetList.get(0).getPurchaseDate());
        assertEquals("COMPUTERS", excelAssetList.get(0).getCategory());
        assertEquals("14,625.00", excelAssetList.get(0).getPurchaseCost());
        assertEquals("355.02", excelAssetList.get(5).getNetBookValue());
    }
}