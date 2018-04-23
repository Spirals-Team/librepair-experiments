package com.epam.brest.course.rest;

import com.epam.brest.course.dto.OrderWithTruckCodeDto;
import com.epam.brest.course.model.Order;
import com.epam.brest.course.rest.config.TestUtil;
import com.epam.brest.course.service.OrderService;
import com.epam.brest.course.utility.data.OrderDto;
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

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:rest.spring.test.xml")
public class OrderRestControllerMockTest {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final double QTY = 23.0;

    private static final int ID_1 = 2;
    private static final String TRUCK_CODE = "BY234";
    private static Integer ID = 1;

    private static final String DATE_STRING = "2006-01-01";
    private static final String DATE_STRING_2 = "2009-01-01";

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private OrderRestController orderRestController;

    //orders
    private Order order;
    private Order order1;

    //order with truckCode
    private OrderWithTruckCodeDto orderWithTruckCodeDto;

    private OrderDto orderDto;

    @Autowired
    private MappingService mappingService;


    @Autowired
    private OrderService orderService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws ParseException {
        order = new Order();
        Date  date = formatter.parse(DATE_STRING);

        Date date1 = formatter.parse(DATE_STRING_2);
        order.setOrderId(ID);
        order.setPetrolQty(QTY);
        order.setTruckId(ID);
        order.setOrderDate(date1);


        order1 = new Order();
        order1.setOrderId(ID_1);
        order1.setPetrolQty(QTY);
        order1.setTruckId(ID_1);
        order1.setOrderDate(date);

        orderDto = new OrderDto();
        orderDto.setOrderId(1);
        orderDto.setPetrolQty(QTY);
        orderDto.setTruckId(ID);

        orderWithTruckCodeDto = new OrderWithTruckCodeDto();
        orderWithTruckCodeDto.setPetrolQty(QTY);
        orderWithTruckCodeDto.setOrderDate(new Date());
        orderWithTruckCodeDto.setOrderId(ID);
        orderWithTruckCodeDto.setTruckCode(TRUCK_CODE);

        mockMvc = MockMvcBuilders.standaloneSetup(orderRestController)
                .setMessageConverters(
                        new MappingJackson2HttpMessageConverter())
                .build();
    }

    @Test
    public void getOrderById() throws Exception {
        LOGGER.debug("test: getOrderById() ");
        Date date = formatter.parse(DATE_STRING_2);
        order.setOrderDate(date);

        when(orderService.getOrderById(ID)).thenReturn(order);

        mockMvc.perform(get("/orders/{orderId}", ID).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("orderId", Matchers.is(ID)))
                .andExpect(jsonPath("petrolQty", Matchers.is(QTY)))
                .andExpect(jsonPath("orderDate", Matchers.is(date.getTime())))
                .andExpect(jsonPath("truckId", Matchers.is(ID)));

        Mockito.verify(orderService).getOrderById(ID);
    }


    @Test
    public void updateOrder() throws Exception {
        LOGGER.debug("test: update() ");

        mockMvc.perform(put("/orders").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(order))
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print()).andExpect(status().isOk());

        Mockito.verify(orderService).updateOrder(order);
    }

    @Test
    public void addOrder() throws Exception {
        LOGGER.debug("test: addOrder() ");

        when(orderService.addOrder(order)).thenReturn(order);

        mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(order))
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print()).andExpect(status().isCreated());

        Mockito.verify(orderService).addOrder(order);
    }

    @Test
    public void deleteOrder() throws Exception {
        LOGGER.debug("test: deleteOrder() ");

        mockMvc.perform(delete("/orders/{orderId}", ID)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isFound());
        Mockito.verify(orderService).deleteOrderById(ID);
    }

    @Test
    public void getAllOrders() throws Exception {
        LOGGER.debug("test: getAllOrders()");

        when(orderService.getAllOrders(null, null)).thenReturn(Arrays.asList(order, order1));

        mockMvc.perform(get("/orders").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0]orderId", Matchers.is(ID)))
                .andExpect(jsonPath("$[0]petrolQty", Matchers.is(QTY)))
                .andExpect(jsonPath("$[0]truckId", Matchers.is(ID)))

                .andExpect(jsonPath("$[1]orderId", Matchers.is(ID_1)))
                .andExpect(jsonPath("$[1]petrolQty", Matchers.is(QTY)))
                .andExpect(jsonPath("$[1]truckId", Matchers.is(ID_1)));

        Mockito.verify(orderService).getAllOrders(null, null);
    }

    @Test
    public void getAllOrdersWithFilter() throws Exception {
        LOGGER.debug("test: getAllOrders()");
           Date  date = formatter.parse(DATE_STRING);
           Date   date1 = formatter.parse(DATE_STRING_2);

        when(orderService.getAllOrders(date, date1)).thenReturn(Arrays.asList(order, order1));

        mockMvc.perform(get("/orders?start=2006-01-01&end=2009-01-01").accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0]orderId", Matchers.is(ID)))
                .andExpect(jsonPath("$[0]petrolQty", Matchers.is(QTY)))
                .andExpect(jsonPath("$[0]orderDate", Matchers.is(1230760800000L)))
                .andExpect(jsonPath("$[0]truckId", Matchers.is(ID)))

                .andExpect(jsonPath("$[1]orderId", Matchers.is(ID_1)))
                .andExpect(jsonPath("$[1]petrolQty", Matchers.is(QTY)))
                .andExpect(jsonPath("$[1]orderDate", Matchers.is(1136066400000L)))
                .andExpect(jsonPath("$[1]truckId", Matchers.is(ID_1)));

        Mockito.verify(orderService).getAllOrders(date, date1);
    }



}