package com.epam.brest.course.rest;


import com.epam.brest.course.dto.TruckWithAvgDto;
import com.epam.brest.course.model.Truck;
import com.epam.brest.course.rest.config.TestUtil;
import com.epam.brest.course.service.TruckService;
import com.epam.brest.course.utility.dozer.MappingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:rest.spring.test.xml")
public class TruckRestControllerMockTest {

    private static final String TRUCK_CODE = "BY2447";
    private static final String DESCRIPTION = "NEW TRUCK";
    private static final String DESCRIPTION_1 = "BLACK TRUCK";
    private static final String TRUCK_CODE1 = "BY2606";
    private static final int TRUCK_ID = 2;
    private static final double AVG = 39.0;
    private static Integer ID = 1;

    /**
     * Log class for debug.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private TruckService truckService;

    @Autowired
    private MappingService mappingService;

    @Autowired
    private TruckRestController truckRestController;


    private MockMvc mockMvc;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private static final String DATE_STRING_2 = "2009-01-01";
    private Truck truck;

    private Truck truck2;

    private TruckWithAvgDto truckWithAvgDto;

    @Before
    public void setUp() throws ParseException {

        truck = new Truck();
        Date date = formatter.parse(DATE_STRING_2);
        truck.setDescriptions(DESCRIPTION);
        truck.setTruckCode(TRUCK_CODE);
        truck.setTruckId(ID);
        truck.setPurchasedDate(date);

        truck2 = new Truck();
        truck2.setDescriptions(DESCRIPTION_1);
        truck2.setTruckCode(TRUCK_CODE1);
        truck2.setTruckId(TRUCK_ID);

        truckWithAvgDto = new TruckWithAvgDto();
        truckWithAvgDto.setTruckId(ID);
        truckWithAvgDto.setTruckCode(TRUCK_CODE);
        truckWithAvgDto.setDescriptions(DESCRIPTION);
        truckWithAvgDto.setAvgPerMonth(AVG);


        mockMvc = MockMvcBuilders.standaloneSetup(truckRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    //getByid
    @Test
    public void getTruckById() throws Exception {
        LOGGER.debug("test: getTruckById()");

        when(truckService.getTruckById(ID)).thenReturn(truckWithAvgDto);
        mockMvc.perform(get("/trucks/{truckId}", ID).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("truckId", Matchers.is(ID)))
                .andExpect(jsonPath("truckCode", Matchers.is(TRUCK_CODE)))
                .andExpect(jsonPath("descriptions", Matchers.is(DESCRIPTION)))
                .andExpect(jsonPath("avgPerMonth", Matchers.is(AVG)));


        Mockito.verify(truckService).getTruckById(ID);
    }

    //update
    @Test
    public void update() throws Exception {
        LOGGER.debug("test: update() ");

        mockMvc.perform(put("/trucks").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(truck))
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print()).andExpect(status().isOk());

        Mockito.verify(truckService).updateTruck(truck);
    }

    //add
    @Test
    public void addTruck() throws Exception {
        LOGGER.debug("test: addTruck() ");

        when(truckService.addTruck(truck)).thenReturn(truck);
        mockMvc.perform(post("/trucks").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(truck))
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print()).andExpect(status().isCreated());

        Mockito.verify(truckService).addTruck(truck);
    }

    //delete
    @Test
    public void deleteTruck() throws Exception {
        LOGGER.debug("test: deleteTruck() ");

        mockMvc.perform(delete("/trucks/{truckId}", ID)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isFound());
        Mockito.verify(truckService).deleteTruckById(ID);
    }


    //getlist
    @Test
    public void getTrucks() throws Exception {
        LOGGER.debug("test: getTrucks()");

        when(truckService.getAllTrucks()).thenReturn(Arrays.asList(truck, truck2));
        mockMvc.perform(get("/trucks").accept(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isOk()) 
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0]truckId", Matchers.is(ID)))
                .andExpect(jsonPath("$[0]truckCode", Matchers.is(TRUCK_CODE)))
                .andExpect(jsonPath("$[0]descriptions", Matchers.is(DESCRIPTION)))

                .andExpect(jsonPath("$[1]truckId", Matchers.is(TRUCK_ID)))
                .andExpect(jsonPath("$[1]truckCode", Matchers.is(TRUCK_CODE1)))
                .andExpect(jsonPath("$[1]descriptions", Matchers.is(DESCRIPTION_1)));

        Mockito.verify(truckService).getAllTrucks();
    }

}
