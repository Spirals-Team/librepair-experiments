package com.epam.brest.course.service;

import com.epam.brest.course.dao.OrderDao;
import com.epam.brest.course.dto.OrderWithTruckCodeDto;
import com.epam.brest.course.model.Order;
import com.epam.brest.course.service.mockConfig.MockConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MockConfig.class, OrderServiceImpl.class})
public class OrderServiceMockTest {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final double PETROL_QTY = 435.3;
    private static final Integer ID = 15;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private static final String DATE_STRING = "2007-01-01";
    private static final String DATE_STRING_2 = "2009-01-01";
    private static int TRUCK_ID = 1;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDao orderDao;

    private Order newOrder;
    private OrderWithTruckCodeDto order;
    private OrderWithTruckCodeDto order1;

    @Before
    public void setup() {
        order = new OrderWithTruckCodeDto();
        order1 = new OrderWithTruckCodeDto();
    }

    @Test
    public void addOrder() throws Exception {
        LOGGER.debug("test: addOrder()");

        Date date = formatter.parse(DATE_STRING_2);

        Order order = new Order(PETROL_QTY, date, TRUCK_ID);

        when(orderDao.addOrder(order))
                .thenReturn(order);

        newOrder = orderService.addOrder(order);

        Assert.assertNotNull(newOrder);
        Assert.assertEquals(newOrder.getTruckId(),
                order.getTruckId());

        Mockito.verify(orderDao)
                .addOrder(order);
    }


    @Test
    public void getOrderById() throws Exception {
        LOGGER.debug("test: getOrderById()");

        Order order = new Order();
        order.setOrderId(ID);
        when(orderDao.getOrderById(ID))
                .thenReturn(order);

        newOrder = orderService.getOrderById(ID);
        Assert.assertEquals(order.getOrderId(),
                order.getOrderId());

        Mockito.verify(orderDao)
                .getOrderById(ID);
    }

    /**
     * @throws Exception in case of rule violation.
     */
    @Test
    public void deleteOrderById() throws Exception {
        LOGGER.debug("test: deleteOrderById()");

        Order order = new Order();
        order.setOrderId(ID);

        orderService.deleteOrderById(ID);

        //verify that dao's delete was called
        Mockito.verify(orderDao).deleteOrderById(ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalArgumentInDelete() throws Exception {
        LOGGER.debug("test: illegalArgumentInDelete()");

        orderService.deleteOrderById(null);
    }


    @Test
    public void getOrders() throws Exception {
        LOGGER.debug("test: getOrders()");

        Order firstOrder = new Order();
        Order secondOrder = new Order();

        when(orderDao.getAllOrders(null, null))
                .thenReturn(Arrays.asList(firstOrder, secondOrder));
        Collection<Order> orders = orderService.getAllOrders(null, null);

        Assert.assertEquals(orders.size(), 2);
        Assert.assertTrue(orders.contains(firstOrder));

        Mockito.verify(orderDao).getAllOrders(null, null);

    }

    @Test
    public void getOrdersWithDates() throws Exception {
        LOGGER.debug("test: getOrdersWithDates()");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date from = sdf.parse("2005-01-01");
        Date to = sdf.parse("2009-01-01");

        Order firstOrder = new Order();
        Order secondOrder = new Order();

        when(orderDao.getAllOrders(from, to))
                .thenReturn(Arrays.asList(firstOrder, secondOrder));

        Collection<Order> orders = orderService.getAllOrders(from, to);

        Assert.assertEquals(orders.size(), 2);
        Assert.assertTrue(orders.contains(firstOrder));
        //verify that dao getAll orders was called
        Mockito.verify(orderDao).getAllOrders(from, to);
    }


    /**
     * @throws Exception is thrown because order cannot be null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void addOrderWithIncompleteArgument() throws Exception {
        Date date = formatter.parse(DATE_STRING);

        orderService.addOrder(new Order(PETROL_QTY,date, null));
    }


    @Test
    public void updateAnOrder() throws Exception {
        LOGGER.debug("test: updateAnOrder()");
        Order order = new Order();
        order.setPetrolQty(PETROL_QTY);

        orderService.updateOrder(order);

        Assert.assertNotNull(order);
        //verify that we called the dao when update occurs
        Mockito.verify(orderDao).updateOrder(order);
    }

    @Test
    public void getAllOrdersWithTruckCode() {
        LOGGER.debug("test: getAllOrdersWithTruckCode()");

        when(orderDao.getAllOrdersWithTruckCode())
                .thenReturn(Arrays.asList(order, order1));

        Collection<OrderWithTruckCodeDto> getAllOrdersWithTruckCode =
                orderService.getAllOrdersWithTruckCode();

        //assertions
        Assert.assertTrue(getAllOrdersWithTruckCode.containsAll(Arrays.asList(order, order1)));
        Mockito.verify(orderDao).getAllOrdersWithTruckCode();
    }


    @Test
    public void findOneOrder_ThrowsIllegalArgs() {

        LOGGER.debug("test: findOneOrder_ThrowsIllegalArgs()");

        when(orderDao.getOrderById(null)).thenThrow(new IllegalArgumentException("") {
        });

        exception.expect(IllegalArgumentException.class);
        orderDao.getOrderById(null);
    }

}
