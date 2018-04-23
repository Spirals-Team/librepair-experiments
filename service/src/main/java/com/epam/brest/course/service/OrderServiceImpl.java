package com.epam.brest.course.service;

import com.epam.brest.course.dao.OrderDao;
import com.epam.brest.course.dto.OrderWithTruckCodeDto;
import com.epam.brest.course.model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Date;

/**
 * Impl class.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    /**
     * logger.
     */
  private static final Logger LOGGER = LogManager.getLogger();

    /**
     * dao.
     */
    @Autowired
    private OrderDao orderDao;

    /**
     * @param orderDao1 .
     */
    public final void setOrderDao(final OrderDao orderDao1) {
        this.orderDao = orderDao1;
    }

    /**
     * @return list.
     * @throws DataAccessException exception.
     */
    @Override
    public final Collection<OrderWithTruckCodeDto> getAllOrdersWithTruckCode()
                                                throws DataAccessException {
        LOGGER.debug("getAllOrdersWithTruckCode()");

        return orderDao.getAllOrdersWithTruckCode();
    }

    /**
     * @param id to get order.
     * @return order.
     * @throws DataAccessException for db.
     */
    @Override
    public final Order getOrderById(final Integer id)
                                        throws DataAccessException {
       LOGGER.debug("getOrderById({})", id);

        Assert.notNull(id, "id cannot be null");
        return orderDao.getOrderById(id);
    }

    /**
     * @param order for add.
     * @return new order.
     * @throws DataAccessException exception.
     */
    @Override
    public final Order addOrder(final Order order)
                                            throws DataAccessException {
        LOGGER.debug("addOrder({})", order);

        Assert.notNull(order, "order cannot be null");
        Assert.notNull(order.getTruckId(), "truckId cannot be null");
        Assert.notNull(order.getPetrolQty(), "petrol cannot be null");
        Assert.notNull(order.getOrderDate(), "date cannot be null");
        return orderDao.addOrder(order);
    }

    /**
     * @param id by id.
     * @throws DataAccessException for db.
     */
    @Override
    public final void deleteOrderById(final Integer id)
                                        throws DataAccessException {
      LOGGER.debug("deleteOrderById({})", id);

        Assert.notNull(id, "id cannot be null");
        orderDao.deleteOrderById(id);
    }

    /**
     * @param order for update.
     * @throws DataAccessException for db.
     */
    @Override
    public final void updateOrder(final Order order)
                                        throws DataAccessException {
      LOGGER.debug("updateOrder({})", order);
        Assert.notNull(order, "order cannot be null");

        orderDao.updateOrder(order);
    }

    /**
     * @return list of all orders.
     * @throws DataAccessException exception.
     */
    @Override
    public final Collection<Order> getAllOrders(final Date start,
                                                        final Date end)
                                                throws DataAccessException {
        LOGGER.debug("getAllOrders()");

        return orderDao.getAllOrders(start, end);
    }


}
