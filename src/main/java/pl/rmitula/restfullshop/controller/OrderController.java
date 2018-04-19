package pl.rmitula.restfullshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.rmitula.restfullshop.model.Order;
import pl.rmitula.restfullshop.model.dto.OrderDto;
import pl.rmitula.restfullshop.service.OrderService;
import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static pl.rmitula.restfullshop.controller.converter.Converter.toOrderDto;

//TODO: Tests
@RestController
public class OrderController {

    private OrderService orderService;

    final String url = "api/orders";

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(url)
    public List<OrderDto> get() {
        return orderService.getOrders().stream().map((Order order) -> toOrderDto(order)).collect(Collectors.toList());
    }

    @GetMapping(path = url + "/{id}")
    public OrderDto findById(@PathVariable(name = "id") long id) {
        return toOrderDto(orderService.findById(id));
    }

    @PostMapping(path = url)
    public Long create(@RequestBody @Valid OrderDto order) {
        return orderService.create(order.getProductId(), order.getUserId(), order.getQuanity(), order.getStatus());
    }

    @GetMapping("api/products/{productId}/orders")
    public List<OrderDto> findByProductId(@PathVariable(name = "productId") Long productId) {
        return orderService.findByProductId(productId).stream().map((Order order) -> toOrderDto(order)).collect(Collectors.toList());
    }

    //TODO: Update order

}

