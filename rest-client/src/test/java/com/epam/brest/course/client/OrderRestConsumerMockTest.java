package com.epam.brest.course.client;


import com.epam.brest.course.dto.OrderWithTruckCodeDto;
import com.epam.brest.course.model.Order;
import com.epam.brest.course.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:rest-client-test.xml")
public class OrderRestConsumerMockTest {

    /**
     * logger.
     */
     private static final Logger LOGGER = LogManager.getLogger();

     private  static final double QTY = 23.0;
     private static final int ID = 1;
     private static final int TWO = 2;

     @Autowired
     private RestTemplate restTemplate;

     @Autowired
     private OrderService orderService;

     private Order order;

     private OrderWithTruckCodeDto orderWithTruckCodeDto;
     private OrderWithTruckCodeDto orderWithTruckCodeDto1;

     private static String DATE_STRING = "2004-02-02";
     private static String DATE_STRING2 = "2009-02-02";


    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void setup() throws ParseException {

        Date date = formatter.parse(DATE_STRING);
        order = new Order();
        order.setTruckId(ID);
        order.setOrderId(TWO);
        order.setOrderDate(date);
        order.setPetrolQty(QTY);

        orderWithTruckCodeDto = new OrderWithTruckCodeDto();
        orderWithTruckCodeDto.setTruckCode("BY2343");
        orderWithTruckCodeDto.setOrderId(1);
        orderWithTruckCodeDto.setOrderDate(date);
        orderWithTruckCodeDto.setPetrolQty(QTY);

        orderWithTruckCodeDto1= new OrderWithTruckCodeDto();
        orderWithTruckCodeDto1.setTruckCode("BY2243");
        orderWithTruckCodeDto1.setOrderId(TWO);
        orderWithTruckCodeDto1.setOrderDate(date);
        orderWithTruckCodeDto1.setPetrolQty(QTY);

    }

    @After
    public void tearDown() {
        Mockito.reset(restTemplate);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getOrders() {
        LOGGER.debug("client test: getOrders()");

        List orders = Arrays.asList(order);
        ResponseEntity entity = new ResponseEntity(orders, HttpStatus.FOUND);

        when(restTemplate.getForEntity("http://localhost:8088/orders", List.class))
                .thenReturn(entity);

        Collection<Order> results
                = orderService.getAllOrders(null, null);

        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());
        Mockito.verify(restTemplate).getForEntity("http://localhost:8088/orders", List.class);
    }


    @SuppressWarnings("unchecked")
    @Test
    public void getOrderById() {
        LOGGER.debug("client test: getOrderById()");

        ResponseEntity entity = new ResponseEntity<>(order, HttpStatus.FOUND);

        when(restTemplate.getForEntity("http://localhost:8088/orders/1", Order.class))
                .thenReturn(entity);

        Order result = orderService.getOrderById(ID);

        Assert.assertNotNull(result);
        Assert.assertEquals(order.getTruckId(), result.getTruckId());

        Mockito.verify(restTemplate)
                .getForEntity("http://localhost:8088/orders/1", Order.class);
    }


    @SuppressWarnings("unchecked")
    @Test
    public void addOrder() {
        LOGGER.debug("client test: addOrder()");

        ResponseEntity entity = new ResponseEntity<>(order, HttpStatus.FOUND);

        when(restTemplate.postForEntity("http://localhost:8088/orders",
                order, Order.class))
                .thenReturn(entity);

        Order result = orderService.addOrder(order);

        Assert.assertNotNull(result);
        Assert.assertEquals(ID, result.getTruckId().intValue());

        Mockito.verify(restTemplate).postForEntity("http://localhost:8088/orders",
                order, Order.class);
    }

    @Test
    public void updateOrder() {
        LOGGER.debug("client test: updateOrder()");

        restTemplate.put("http://localhost:8088/orders", order);
        Mockito.verify(restTemplate).put("http://localhost:8088/orders", order);
    }

    @Test
    public void deleteOrder() {
        LOGGER.debug("client test: deleteOrder()");
        restTemplate.delete("http://localhost:8088/orders/" + ID);

       Mockito.verify(restTemplate).delete("http://localhost:8088/orders/" + ID);
    }
}
