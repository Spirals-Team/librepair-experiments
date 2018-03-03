package com.javapro.costs.repository.memory;

import com.javapro.costs.model.Purchase;
import com.javapro.costs.repository.PurchaseRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Created by Vladimir_Vysokomorny on 29-Jun-17.
 */
public class InMemoryPurchaseRepositoryImpl implements PurchaseRepository {

    private Map<Long, Purchase> repository = new ConcurrentHashMap<>();
    private AtomicLong counter = new AtomicLong(0);

    @Override
    public Purchase get(long id) {
        return repository.get(id);
    }

    @Override
    public Purchase save(Purchase purchase) {
        if (purchase.isNew()) {
            purchase.setId(counter.getAndIncrement());
        }
        repository.put(purchase.getId(), purchase);
        return purchase;
    }

    @Override
    public boolean delete(long id) {
        return repository.remove(id) != null;
    }

    @Override
    public List<Purchase> getAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public List<Purchase> getBetween(LocalDate start, LocalDate end) {
        return repository.values().stream().filter(p ->
                start.compareTo(p.getCreatedDate()) >= 0 && end.compareTo(p.getCreatedDate()) <= 0).collect(Collectors.toList());
    }
}
