package com.javapro.costs.service;

import com.javapro.costs.exception.NotFoundException;
import com.javapro.costs.model.Purchase;
import com.javapro.costs.repository.datajpa.DataJpaPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private DataJpaPurchaseRepository repository;

    @Override
    public Purchase get(long id) throws NotFoundException {
        Purchase result = repository.findOne(id);
        if (result == null) {
            throw new NotFoundException("Can't find purchase with id=" + id);
        }
        return result;
    }

    @Override
    public Purchase save(Purchase purchase) throws NotFoundException {
        Purchase result = repository.save(purchase);
        if (result == null) {
            throw new NotFoundException("Purchase " + purchase + " wasn't created/updated.");
        }
        return result;

    }

    @Override
    public void delete(long id) throws NotFoundException {
        repository.delete(id);
    }

    @Override
    public List<Purchase> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Purchase> getBetweenDateTimes(LocalDate start, LocalDate end) {
        return repository.getBetween(start, end);
    }
}
