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
import io.github.fasset.fasset.model.brief.CategoryBrief;
import io.github.fasset.fasset.model.brief.ServiceOutletBrief;
import io.github.fasset.fasset.repository.CategoryBriefRepository;
import io.github.fasset.fasset.repository.ServiceOutletBriefRepository;
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
public class BriefControllerTestIT {

    private MockMvc mockMvc;
    @Qualifier("briefController")
    @Autowired
    private BriefController briefController;
    @Qualifier("serviceOutletBriefRepository")
    @Autowired
    private ServiceOutletBriefRepository serviceOutletBriefRepository;
    @Qualifier("categoryBriefRepository")
    @Autowired
    private CategoryBriefRepository categoryBriefRepository;


    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.standaloneSetup(briefController).build();
    }

    @Test
    public void getCategoryBriefsTemplateWorks() throws Exception {
        this.mockMvc.perform(get("/briefs/categories")).andExpect(status().isOk()).andExpect(view().name("briefs/categoryBriefs")).andDo(print());
    }

    @Test
    public void getServiceOutletBriefsTemplateWorks() throws Exception {
        this.mockMvc.perform(get("/briefs/serviceOutlets")).andExpect(status().isOk()).andExpect(view().name("briefs/serviceOutletBriefs")).andDo(print());
    }


    @Test
    public void getCategoryGivenIdWorks() throws Exception {
        CategoryBrief brief1 = new CategoryBrief();
        brief1.setAccruedDepreciation(Money.of(300.00, "KES"));
        brief1.setPoll(20);
        brief1.setDesignation("Computers");
        brief1.setNetBookValue(Money.of(6800.00, "KES"));
        brief1.setPurchaseCost(Money.of(7100, "KES"));
        CategoryBrief persisted1 = categoryBriefRepository.save(brief1);
        int id = persisted1.getId();

        this.mockMvc.perform(get("/briefs/categories/data/{nomenclature}", id)).andExpect(status().isOk()).andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("nomenclature", is(id))).andExpect(jsonPath("poll", is(20))).andExpect(jsonPath("designation", is("Computers"))).andExpect(jsonPath("netBookValue", is(6800.0)))
            .andExpect(jsonPath("purchaseCost", is(7100.0))).andDo(print());
    }

    @Test
    public void getCategoryBriefsWorks() throws Exception {

        CategoryBrief brief1 = new CategoryBrief();
        brief1.setAccruedDepreciation(Money.of(300.00, "KES"));
        brief1.setPoll(20);
        brief1.setDesignation("Computers");
        brief1.setNetBookValue(Money.of(6800.00, "KES"));
        brief1.setPurchaseCost(Money.of(7100, "KES"));

        CategoryBrief brief2 = new CategoryBrief();
        brief2.setAccruedDepreciation(Money.of(120.00, "KES"));
        brief2.setPoll(20);
        brief2.setDesignation("Furniture");
        brief2.setNetBookValue(Money.of(5000.00, "KES"));
        brief2.setPurchaseCost(Money.of(5120, "KES"));

        CategoryBrief persisted1 = categoryBriefRepository.save(brief1);
        CategoryBrief persisted2 = categoryBriefRepository.save(brief2);

        int id = persisted1.getId();
        int id2 = persisted2.getId();

        this.mockMvc.perform(get("/briefs/categories/data")).andExpect(status().isOk()).andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].nomenclature", is(id))).andExpect(jsonPath("$[0].poll", is(brief1.getPoll()))).andExpect(jsonPath("$[0].designation", is(brief1.getDesignation())))
            .andExpect(jsonPath("$[0].netBookValue", is(brief1.getNetBookValue().getNumber().doubleValue())))
            .andExpect(jsonPath("$[0].purchaseCost", is(brief1.getPurchaseCost().getNumber().doubleValue()))).andExpect(jsonPath("$[1].nomenclature", is(id2)))
            .andExpect(jsonPath("$[1].poll", is(brief2.getPoll()))).andExpect(jsonPath("$[1].designation", is(brief2.getDesignation())))
            .andExpect(jsonPath("$[1].netBookValue", is(brief2.getNetBookValue().getNumber().doubleValue())))
            .andExpect(jsonPath("$[1].purchaseCost", is(brief2.getPurchaseCost().getNumber().doubleValue()))).andDo(print());

    }

    @Test
    public void getServiceOutletBriefGivenIdWorks() throws Exception {

        ServiceOutletBrief solBrief1 = new ServiceOutletBrief();
        solBrief1.setAccruedDepreciation(Money.of(100.00, "KES"));
        solBrief1.setPoll(3);
        solBrief1.setDesignation("003");
        solBrief1.setNetBookValue(Money.of(6500.00, "KES"));
        solBrief1.setPurchaseCost(Money.of(7000.00, "KES"));

        ServiceOutletBrief persistedBrief = serviceOutletBriefRepository.save(solBrief1);

        int id = persistedBrief.getId();


        this.mockMvc.perform(get("/briefs/serviceOutlets/data/{nomenclature}", id)).andExpect(status().isOk()).andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("nomenclature", is(id))).andExpect(jsonPath("poll", is(solBrief1.getPoll()))).andExpect(jsonPath("designation", is(solBrief1.getDesignation())))
            .andExpect(jsonPath("purchaseCost", is(7000.0))).andExpect(jsonPath("netBookValue", is(6500.0))).andDo(print());
    }

    @Test
    public void getServiceOutletBriefsWorks() throws Exception {

        ServiceOutletBrief solBrief1 = new ServiceOutletBrief();
        solBrief1.setAccruedDepreciation(Money.of(100.00, "KES"));
        solBrief1.setPoll(3);
        solBrief1.setDesignation("003");
        solBrief1.setNetBookValue(Money.of(6500.00, "KES"));
        solBrief1.setPurchaseCost(Money.of(7000.00, "KES"));

        ServiceOutletBrief solBrief2 = new ServiceOutletBrief();
        solBrief2.setAccruedDepreciation(Money.of(1000.00, "KES"));
        solBrief2.setPoll(35);
        solBrief2.setDesignation("003");
        solBrief2.setNetBookValue(Money.of(5300.00, "KES"));
        solBrief2.setPurchaseCost(Money.of(6000.00, "KES"));

        ServiceOutletBrief persisted1 = serviceOutletBriefRepository.save(solBrief1);
        ServiceOutletBrief persisted2 = serviceOutletBriefRepository.save(solBrief2);

        int id = persisted1.getId();
        int id2 = persisted2.getId();


        this.mockMvc.perform(get("/briefs/serviceOutlets/data")).andExpect(status().isOk()).andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].nomenclature", is(id))).andExpect(jsonPath("$[0].poll", is(solBrief1.getPoll()))).andExpect(jsonPath("$[0].designation", is(solBrief1.getDesignation())))
            .andExpect(jsonPath("$[0].purchaseCost", is(solBrief1.getPurchaseCost().getNumber().doubleValue())))
            .andExpect(jsonPath("$[0].netBookValue", is(solBrief1.getNetBookValue().getNumber().doubleValue()))).andExpect(jsonPath("$[1].nomenclature", is(id2)))
            .andExpect(jsonPath("$[1].poll", is(solBrief2.getPoll()))).andExpect(jsonPath("$[1].designation", is(solBrief2.getDesignation())))
            .andExpect(jsonPath("$[1].purchaseCost", is(solBrief2.getPurchaseCost().getNumber().doubleValue())))
            .andExpect(jsonPath("$[1].netBookValue", is(solBrief2.getNetBookValue().getNumber().doubleValue()))).andDo(print());
    }
}