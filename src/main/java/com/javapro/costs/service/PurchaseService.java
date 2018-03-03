package com.javapro.costs.service;

import com.javapro.costs.exception.NotFoundException;
import com.javapro.costs.model.Purchase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Vladimir_Vysokomorny on 28-Jun-17.
 */
public interface PurchaseService {
    Purchase get(long id) throws NotFoundException;
    Purchase save(Purchase purchase) throws NotFoundException;
    void delete(long id) throws NotFoundException;

    List<Purchase> getAll();
    List<Purchase> getBetweenDateTimes(LocalDate start, LocalDate end);
}
