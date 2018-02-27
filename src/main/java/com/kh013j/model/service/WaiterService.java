package com.kh013j.model.service;

import com.kh013j.model.domain.OrderedDish;
import com.kh013j.model.domain.Status;
import com.kh013j.model.domain.Tables;
import com.kh013j.model.domain.User;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

public class WaiterService {
    @Resource
    OrderedDishServiceImpl orderedDishService;

    @Resource
    Status status;

    @Resource
    OrderServiceImpl orderService;

    public List<OrderedDish> demandingDelivery() {
        return orderedDishService.findAll().stream().filter(orderedDish -> orderedDish.getStatus().equals(Status.DELIVERY)).collect(Collectors.toList());
    }

    public List<Tables> waitersTables(User waiter) {
        return orderService.findTableInfoForWaiter().stream().filter(tables -> tables.getCurrentWaiter().equals(waiter)).collect(Collectors.toList());
    }
}
