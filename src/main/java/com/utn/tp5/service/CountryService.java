package com.utn.tp5.service;

import com.utn.tp5.persistence.CountryPersistence;
import com.utn.tp5.model.Country;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CountryService {

    @Autowired
    private CountryPersistence countryPersistence;

    public List<Country> getAll() {
        return countryPersistence.findAll();
    }

    public Country getById(Long id) {
        return countryPersistence.getOne(id);
    }

    public boolean saveCountry(Country c) {
        boolean rtn = false;
        Country saved = countryPersistence.save(c);
        if (saved.equals(c)) {
            rtn = true;
        }
        return rtn;
    }

    public boolean deleteCountry(String name) {
        return countryPersistence.deleteByName(name);
    }
}
