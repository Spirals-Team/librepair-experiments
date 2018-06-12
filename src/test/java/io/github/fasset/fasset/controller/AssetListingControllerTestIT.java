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
package io.github.fasset.fasset.controller;

import io.github.fasset.fasset.TestUtil;
import io.github.fasset.fasset.model.FixedAsset;
import io.github.fasset.fasset.repository.FixedAssetRepository;
import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//@WebMvcTest // we needed the entire object graph inorder to interact with entity manager factory
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebMvc
public class AssetListingControllerTestIT {

    private MockMvc mockMvc;

    @Qualifier("assetListingController")
    @Autowired
    private AssetListingController assetListingController;

    @Qualifier("fixedAssetRepository")
    @Autowired
    private FixedAssetRepository fixedAssetRepository;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(assetListingController).build();
    }

    @Test
    public void showAssetListTemplateWorks() throws Exception {
        this.mockMvc.perform(get("/assetList")).andExpect(status().isOk()).andExpect(view().name("reports/allAssets")).andDo(print());
    }

    @Test
    public void getMonthGivenIdWorks() throws Exception {

        LocalDate purchaseDate = LocalDate.now();

        FixedAsset fixedAsset =
            new FixedAsset().setSolId("001").setPurchaseDate(purchaseDate).setCategory("Electronics").setBarcode("abc").setAssetDescription("Cooker").setPurchaseCost(Money.of(200, "KES"))
                .setNetBookValue(Money.of(150, "KES"));

        FixedAsset persistedAsset = fixedAssetRepository.save(fixedAsset);

        int id = persistedAsset.getId();

        this.mockMvc.perform(get("/listing/assets/data/{nomenclature}", id)).andExpect(status().isOk()).andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("nomenclature", is(id))).andExpect(jsonPath("solId", is("001"))).andExpect(jsonPath("barcode", is("abc"))).andExpect(jsonPath("assetDescription", is("Cooker")))
            .andExpect(jsonPath("category", is("Electronics"))).andExpect(jsonPath("purchaseCost", is(200.0))).andExpect(jsonPath("netBookValue", is(150.0)))
            .andExpect(jsonPath("purchaseDate[0]", is(purchaseDate.getYear()))).andExpect(jsonPath("purchaseDate[1]", is(purchaseDate.getMonthValue())))
            .andExpect(jsonPath("purchaseDate[2]", is(purchaseDate.getDayOfMonth()))).andDo(print());
    }

    @Test
    public void fetchAllAssetsWorks() throws Exception {

        LocalDate purchaseDate = LocalDate.now();

        FixedAsset fixedAsset =
            new FixedAsset().setSolId("001").setPurchaseDate(purchaseDate).setCategory("Electronics").setBarcode("abc").setAssetDescription("Cooker").setPurchaseCost(Money.of(200, "KES"))
                .setNetBookValue(Money.of(150, "KES"));

        FixedAsset fixedAsset2 =
            new FixedAsset().setSolId("003").setPurchaseDate(purchaseDate).setCategory("Computers").setBarcode("nbc").setAssetDescription("Laptop").setPurchaseCost(Money.of(60000, "KES"))
                .setNetBookValue(Money.of(58500, "KES"));

        FixedAsset persistedAsset = fixedAssetRepository.save(fixedAsset);
        FixedAsset persistedAsset2 = fixedAssetRepository.save(fixedAsset2);

        int id = persistedAsset.getId();
        int id2 = persistedAsset2.getId();


        this.mockMvc.perform(get("/listing/assets/data")).andExpect(status().isOk()).andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].nomenclature", is(id))).andExpect(jsonPath("$[0].solId", is("001"))).andExpect(jsonPath("$[0].barcode", is("abc")))
            .andExpect(jsonPath("$[0].assetDescription", is("Cooker"))).andExpect(jsonPath("$[0].purchaseDate[0]", is(purchaseDate.getYear())))
            .andExpect(jsonPath("$[0].purchaseDate[1]", is(purchaseDate.getMonthValue()))).andExpect(jsonPath("$[0].purchaseDate[2]", is(purchaseDate.getDayOfMonth())))
            .andExpect(jsonPath("$[0].category", is("Electronics"))).andExpect(jsonPath("$[0].purchaseCost", is(200.0))).andExpect(jsonPath("$[0].netBookValue", is(150.0)))
            .andExpect(jsonPath("$[1].nomenclature", is(id2))).andExpect(jsonPath("$[1].solId", is("003"))).andExpect(jsonPath("$[1].barcode", is("nbc")))
            .andExpect(jsonPath("$[1].assetDescription", is("Laptop"))).andExpect(jsonPath("$[1].purchaseDate[0]", is(purchaseDate.getYear())))
            .andExpect(jsonPath("$[1].purchaseDate[1]", is(purchaseDate.getMonthValue()))).andExpect(jsonPath("$[1].purchaseDate[2]", is(purchaseDate.getDayOfMonth())))
            .andExpect(jsonPath("$[1].category", is("Computers"))).andExpect(jsonPath("$[1].purchaseCost", is(60000.0))).andExpect(jsonPath("$[1].netBookValue", is(58500.0))).andDo(print());

    }
}