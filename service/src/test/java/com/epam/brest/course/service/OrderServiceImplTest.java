package com.epam.brest.course.service;

import com.epam.brest.course.dto.OrderWithTruckCodeDto;
import com.epam.brest.course.model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-db-spring.xml",
        "classpath:bean-test.xml", "classpath:dao.xml"})
@Transactional
@Rollback
public class OrderServiceImplTest {

    /*** logger*/
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int ORDER_ID_1 = 1;
    private static final Double PETROL_QTY = 234.0;
    private static final int ONE = 1;
    private static final int ACTUAL = 3;
    private static final int TRUCK_ID = 4;
    private static final int SIX = 6;

    @Autowired
    private OrderService orderService;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private static final String DATE_STRING = "2007-01-01";

    private static final String DATE_STRING_2 = "2009-01-01";

    @Test
    public void saveOrder() throws Exception {
        LOGGER.debug("test: saveOrder()");

        Collection<Order> orders = orderService.getAllOrders(null, null);
        int sizeBeforeAdd = orders.size();

        Date date = formatter.parse(DATE_STRING);

        Order order = new Order();
        order.setOrderId(ORDER_ID_1);
        order.setPetrolQty(PETROL_QTY);
        order.setOrderDate(date);
        order.setTruckId(ONE);

        //perform save
        Order newOrder = orderService.addOrder(order);

        //assertions
        Assert.assertNotNull(order.getOrderId());
        Assert.assertNotNull(newOrder.getOrderId());
        Assert.assertTrue(newOrder.getPetrolQty().equals(order.getPetrolQty()));
        Assert.assertTrue((sizeBeforeAdd + 1) == orderService.getAllOrders(null, null).size());
    }


    @Test
    public void getOrderById() throws Exception {
        LOGGER.debug("test: getOrderById()");
        Date date = formatter.parse(DATE_STRING);

        Order order = new Order();
        order.setTruckId(ONE);
        order.setPetrolQty(PETROL_QTY);
        order.setOrderDate(date);
        Order savedOrder = orderService.addOrder(order);
        Order orderFound = orderService.getOrderById(savedOrder.getOrderId());

        //assertions
        Assert.assertNotNull(orderFound);
        Assert.assertTrue(orderFound.getPetrolQty().equals(savedOrder.getPetrolQty()));
    }


    @Test
    public void updateOrder() throws Exception {
        LOGGER.debug("test: updateOrder()");

        Order order = orderService.getOrderById(ONE);
        //confirm we got the right order
        Assert.assertSame((order.getTruckId()), ACTUAL);

        //give order a new truck
        order.setTruckId(TRUCK_ID);
        orderService.updateOrder(order);

        Order orderWithUpdate = orderService.getOrderById(ONE);

        // assert to confirm result
        Assert.assertNotNull(order);
        Assert.assertTrue(order.getTruckId().equals(orderWithUpdate.getTruckId()));
    }

    @Test
    public void deleteOrderById() throws Exception {
        LOGGER.debug("test: deleteById()");
        int orderListSize = orderService.getAllOrders(null, null).size();

        //get this order by id and delete
        orderService.deleteOrderById(ONE);
        //assertions
        Assert.assertTrue(orderListSize > orderService.getAllOrders(null, null).size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteOrderByIdWithNull() throws Exception {
        LOGGER.debug("test: deleteById()");
        orderService.deleteOrderById(null);
    }

    @Test
    public void getAllTrucksWithAvg() throws Exception {
        LOGGER.debug("test: getAllTrucksWithAvg()");

        Collection<OrderWithTruckCodeDto> orders =
                orderService.getAllOrdersWithTruckCode();
        Assert.assertNotNull(orders);
        Assert.assertTrue(orders.size() > SIX);
    }


}

