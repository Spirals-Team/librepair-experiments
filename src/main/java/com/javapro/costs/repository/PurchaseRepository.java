package com.javapro.costs.repository;

import com.javapro.costs.model.Purchase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Vladimir_Vysokomorny on 29-Jun-17.
 */
public interface PurchaseRepository {
    Purchase get(long id);

    Purchase save(Purchase purchase);

    boolean delete(long id);

    List<Purchase> getAll();

    List<Purchase> getBetween(LocalDate start, LocalDate end);
}
