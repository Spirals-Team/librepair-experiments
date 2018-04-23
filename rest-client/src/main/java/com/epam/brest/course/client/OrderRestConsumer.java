package com.epam.brest.course.client;

import com.epam.brest.course.dto.OrderWithTruckCodeDto;
import com.epam.brest.course.model.Order;
import com.epam.brest.course.service.OrderService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *This is the client service class for order.
 */
public class OrderRestConsumer  implements OrderService {

    /**
     * logger.
     */
     private static final Logger LOGGER = LogManager.getLogger();
    /**
     *url.
     */
    private String url;
    /**
     *rest template.
     */
    private RestTemplate restTemplate;

    /**
     * @param url1 url for getting resource.
     * @param restTemplate1 restTemplate.
     */
    public OrderRestConsumer(final String url1,
                             final RestTemplate restTemplate1) {
        url = url1;
        this.restTemplate = restTemplate1;
    }

    /**
     * @return list of trucks with code.
     * @throws DataAccessException exception.
     */
    @SuppressWarnings("unchecked")
    @Override
    public final Collection<OrderWithTruckCodeDto> getAllOrdersWithTruckCode()
                                                throws DataAccessException {
     LOGGER.debug("getAllOrdersWithTruckCode()");

      ResponseEntity responseEntity =
                            restTemplate.getForEntity(url
                                    + "/ordersWithTruckCode",  List.class);

        List<OrderWithTruckCodeDto> orders =
                        (List<OrderWithTruckCodeDto>) responseEntity.getBody();
        return orders;
    }

    /**
     * @param id to get order.
     * @return an order from db.
     * @throws DataAccessException exception.
     */
    @Override
    public final Order getOrderById(final Integer id)
                                    throws DataAccessException {

        LOGGER.debug("getOrderById({})", id);
        ResponseEntity<Order> responseEntity =
                        restTemplate.getForEntity(url + "/" + id, Order.class);

        Order order = responseEntity.getBody();

        return order;
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
        ResponseEntity<Order> responseEntity =
                        restTemplate.postForEntity(url, order, Order.class);
        Order result = responseEntity.getBody();

        return result;
    }

    /**
     * @param id by id.
     * @throws DataAccessException exception.
     */
    @Override
    public final void deleteOrderById(final Integer id)
                                        throws DataAccessException {
        LOGGER.debug("deleteOrderById({})", id);

           restTemplate.delete(url + "/ " + id);
    }

    /**
     * @param order for update.
     * @throws DataAccessException exception.
     */
    @Override
    public final void updateOrder(final Order order)
                                    throws DataAccessException {
        LOGGER.debug("updateOrder({})", order);

        restTemplate.put(url, order);
    }

    /**
     * @return list of orders.
     * @throws DataAccessException exception.
     */
    @SuppressWarnings("unchecked")
    @Override
    public final Collection<Order> getAllOrders(final Date start,
                                                final Date end)
                                    throws DataAccessException {
        LOGGER.debug("getAllOrders({})");

        ResponseEntity responseEntity =
                restTemplate.getForEntity(url, List.class);

        List<Order> orders = (List<Order>) responseEntity.getBody();

           responseEntity.getBody();
        return orders;
    }

}
