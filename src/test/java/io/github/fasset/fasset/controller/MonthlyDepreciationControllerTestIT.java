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

import io.github.fasset.fasset.model.depreciation.MonthlyAssetDepreciation;
import io.github.fasset.fasset.repository.MonthlyAssetDepreciationRepository;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//@WebMvcTest // we needed the entire object graph inorder to interact with entity manager factory
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebMvc
public class MonthlyDepreciationControllerTestIT {

    private MockMvc mockMvc;
    @Qualifier("monthlyDepreciationController")
    @Autowired
    private MonthlyDepreciationController monthlyDepreciationController;
    @Qualifier("monthlyAssetDepreciationRepository")
    @Autowired
    private MonthlyAssetDepreciationRepository monthlyAssetDepreciationRepository;


    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.standaloneSetup(monthlyDepreciationController).build();
    }

    @Test
    public void goToAssetMonthlyDepreciationWorks() throws Exception {
        this.mockMvc.perform(get("/reports/depreciations/assets")).andExpect(status().isOk()).andExpect(view().name("reports/monthlyAsset")).andDo(print());
    }

    @Test
    public void goToSolMonthlyDepreciationWorks() throws Exception {
        this.mockMvc.perform(get("/reports/depreciations/sols")).andExpect(status().isOk()).andExpect(view().name("reports/monthlySol")).andDo(print());
    }

    @Test
    public void goToCategoryMonthlyDepreciationWorks() throws Exception {
        this.mockMvc.perform(get("/reports/depreciations/categories")).andExpect(status().isOk()).andExpect(view().name("reports/monthlyCategory")).andDo(print());
    }

    @Test
    public void monthlyAssetDepreciationDataWorks() throws Exception {

        MonthlyAssetDepreciation assetDepreciation1 = new MonthlyAssetDepreciation();
        assetDepreciation1.setAssetId(20).setYear(2018).setJan(101.0).setFeb(102.1).setMar(103.2).setApr(104.3).setMay(105.4).setJun(106.5).setJul(107.6).setAug(108.7).setSep(109.8).setOct(110.9)
            .setNov(111.99).setDec(112.12);

        MonthlyAssetDepreciation persisted1 = monthlyAssetDepreciationRepository.save(assetDepreciation1);
        int id = persisted1.getId();

        MonthlyAssetDepreciation assetDepreciation2 = new MonthlyAssetDepreciation();
        assetDepreciation2.setAssetId(21).setYear(2018).setJan(201.0).setFeb(202.1).setMar(203.2).setApr(204.3).setMay(205.4).setJun(206.5).setJul(207.6).setAug(208.7).setSep(209.8).setOct(210.9)
            .setNov(211.99).setDec(212.12);

        MonthlyAssetDepreciation persisted2 = monthlyAssetDepreciationRepository.save(assetDepreciation2);
        int id2 = persisted2.getId();

        this.mockMvc.perform(get("/reports/depreciations/assets/data")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].nomenclature", is(id)))
            .andExpect(jsonPath("$[0].assetId", is(assetDepreciation1.getAssetId()))).andExpect(jsonPath("$[0].year", is(assetDepreciation1.getYear())))
            .andExpect(jsonPath("$[0].jan", is(assetDepreciation1.getJan()))).andExpect(jsonPath("$[0].feb", is(assetDepreciation1.getFeb())))
            .andExpect(jsonPath("$[0].mar", is(assetDepreciation1.getMar()))).andExpect(jsonPath("$[0].apr", is(assetDepreciation1.getApr())))
            .andExpect(jsonPath("$[0].may", is(assetDepreciation1.getMay()))).andExpect(jsonPath("$[0].jun", is(assetDepreciation1.getJun())))
            .andExpect(jsonPath("$[0].jul", is(assetDepreciation1.getJul()))).andExpect(jsonPath("$[0].aug", is(assetDepreciation1.getAug())))
            .andExpect(jsonPath("$[0].sep", is(assetDepreciation1.getSep()))).andExpect(jsonPath("$[0].oct", is(assetDepreciation1.getOct())))
            .andExpect(jsonPath("$[0].nov", is(assetDepreciation1.getNov()))).andExpect(jsonPath("$[0].dec", is(assetDepreciation1.getDec()))).andExpect(jsonPath("$[1].nomenclature", is(id2)))
            .andExpect(jsonPath("$[1].assetId", is(assetDepreciation2.getAssetId()))).andExpect(jsonPath("$[1].year", is(assetDepreciation2.getYear())))
            .andExpect(jsonPath("$[1].jan", is(assetDepreciation2.getJan()))).andExpect(jsonPath("$[1].feb", is(assetDepreciation2.getFeb())))
            .andExpect(jsonPath("$[1].mar", is(assetDepreciation2.getMar()))).andExpect(jsonPath("$[1].apr", is(assetDepreciation2.getApr())))
            .andExpect(jsonPath("$[1].may", is(assetDepreciation2.getMay()))).andExpect(jsonPath("$[1].jun", is(assetDepreciation2.getJun())))
            .andExpect(jsonPath("$[1].jul", is(assetDepreciation2.getJul()))).andExpect(jsonPath("$[1].aug", is(assetDepreciation2.getAug())))
            .andExpect(jsonPath("$[1].sep", is(assetDepreciation2.getSep()))).andExpect(jsonPath("$[1].oct", is(assetDepreciation2.getOct())))
            .andExpect(jsonPath("$[1].nov", is(assetDepreciation2.getNov()))).andExpect(jsonPath("$[1].dec", is(assetDepreciation2.getDec()))).andDo(print());
    }
}