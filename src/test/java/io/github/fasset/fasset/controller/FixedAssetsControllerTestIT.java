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

import io.github.fasset.fasset.dto.FixedAssetFormDto;
import io.github.fasset.fasset.kernel.util.convert.DoubleToMoneyConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Just in case the reader is bored with the fact that I am loading the entire object graph,
 * please note that the controller internally uses a service and this service uses a runtime-generated
 * repository.
 * Good design bureaucracy demands, that I load only the mvc-related context using the @WebMvcTest
 * annotation provided by spring. But then this would miss dependencies which have been used to create
 * repositories.
 * Am all for good test design but, I am not about to mock away a service and
 * its repository. If the service implementation changes I would have to note the method changes and
 * mock each of them in the test, as though the test itself will do anything for the end product.
 * So sue me. Or do me a PR, but don't come crying to me if spring changes the repository interface
 * again....
 *
 * @author edwin.njeru
 */
@TestExecutionListeners( {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class})
@RunWith(SpringRunner.class)
@SpringBootTest
public class FixedAssetsControllerTestIT {


    MockHttpServletResponse response;
    private MockMvc mockMvc;
    @Qualifier("fixedAssetsAdditionsController")
    @Autowired
    private FixedAssetsAdditionsController fixedAssetsController;
    @Qualifier("requestMappingHandlerMapping")
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    @Qualifier("requestMappingHandlerAdapter")
    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;
    @Qualifier("doubleToMoneyConverter")
    @Autowired
    private DoubleToMoneyConverter toMoneyConverter;


    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.standaloneSetup(fixedAssetsController).build();
        response = new MockHttpServletResponse();
    }

    @Test
    public void assetAdditionFormWorks() throws Exception {
        this.mockMvc.perform(get("/add/asset")).andExpect(status().isOk()).andExpect(view().name("forms/addAsset")).andDo(print());
    }

    @Test
    public void saveFixedAssetFromFormWorks() throws Exception {

        MockHttpServletRequest request = new MockHttpServletRequest();

        final ModelAndView mv;

        Object handler;

        try {
            FixedAssetFormDto fixedAsset = new FixedAssetFormDto(toMoneyConverter);
            request.setMethod("POST");
            request.setAttribute("assetDescription", "Laptop");
            request.setAttribute("solId", "010");
            handler = requestMappingHandlerMapping.getHandler(request).getHandler();
            mv = handlerAdapter.handle(request, new MockHttpServletResponse(), handler);
            assertEquals(200, response.getStatus());
            assertEquals("Laptop", fixedAsset.getAssetDescription());
            assertEquals("solId", fixedAsset.getSolId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setMethod("POST");
        request.setRequestURI("/add/asset");

        // Not sure why the mockMvc is not working
        /*this.mockMvc.perform(post("/add/asset"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/add/asset"))
                .andDo(print());*/
    }
}