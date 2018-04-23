package com.epam.brest.course.rest;


import com.epam.brest.course.model.Order;
import com.epam.brest.course.service.OrderService;
import com.epam.brest.course.utility.data.OrderDto;
import com.epam.brest.course.utility.dozer.MappingService;
import com.epam.brest.course.utility.validator.OrderValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * rest controller for order.
 */
@CrossOrigin
@RestController
public class OrderRestController {

    /**
     * Log class for debug.
     */
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     * service.
     */
    @Autowired
    private OrderService orderService;
    /**
     * simple formatter.
     */
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * mapping service.
     */
    @Autowired
    private MappingService mappingService;

    /**
     *
     * @param binder binding datas and error check.
     */
    @InitBinder
    protected final void initBinder(final WebDataBinder binder) {
        binder.setValidator(new OrderValidator());
        binder.setMessageCodesResolver(new DefaultMessageCodesResolver());
        formatter.setLenient(false);
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(formatter, true));
    }

    /**
     * @param orderId for get.
     * @return order.
     */
    @GetMapping("/orders/{orderId}")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public final OrderDto getOrderId(@PathVariable(value = "orderId")
                                                    final Integer orderId) {
        LOGGER.debug("getOrderId({})", orderId);

        Order order = orderService.getOrderById(orderId);
        return mappingService.map(order, OrderDto.class);
    }

    /**
     * @param orderDto for add.
     * @return persisted model through dto.
     */
    @PostMapping(value = "/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public final OrderDto addOrder(@Valid @RequestBody
                                                final OrderDto orderDto) {
        LOGGER.debug("addOrder({})", orderDto);

        Order mappedOrder = mappingService.map(orderDto, Order.class);
        Order persistedOrder = orderService.addOrder(mappedOrder);
        return mappingService.map(persistedOrder, OrderDto.class);
    }

    /**
     * @param orderDto for update.
     */
    @PutMapping(value = "/orders")
    @ResponseStatus(HttpStatus.OK)
    public final void updateOrder(@Valid @RequestBody final OrderDto orderDto) {
        LOGGER.debug("updateOrder({})", orderDto);

        // transfer data to model then update
        Order mappedOrder = mappingService.map(orderDto, Order.class);
        orderService.updateOrder(mappedOrder);
    }

    /**
     * @param orderId param.
     */
    @DeleteMapping(value = "/orders/{orderId}")
    @ResponseStatus(HttpStatus.FOUND)
    public final void deleteOrder(@PathVariable(value = "orderId")
                                                    final Integer orderId) {
        LOGGER.debug("deleteOrder({})", orderId);
        //delete
        orderService.deleteOrderById(orderId);
    }

    /**
     * @param start date.
     * @param end   date.
     * @return order list all or filtered
     * <p>
     */
    @GetMapping(value = "/orders")
    public final Collection<OrderDto> getOrders(
            @RequestParam(value = "start", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") final Date start,

            @RequestParam(value = "end", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") final Date end) {
        LOGGER.debug("getOrders()");

        Collection<Order> orders = orderService.getAllOrders(start, end);
        return mappingService.map(orders, OrderDto.class);
    }

}
