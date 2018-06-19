package com.utn.tp5.service;

import com.utn.tp5.persistence.CityPersistence;
import com.utn.tp5.model.City;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CityService {

    @Autowired
    private CityPersistence cityPersistence;

    public List<City> getAll() {
        return cityPersistence.findAll();
    }

    public City getById(Long id) {
        return cityPersistence.getOne(id);
    }

    public boolean saveCity(City c) {
        City saved = cityPersistence.save(c);
        boolean rtn = false;
        if (saved.equals(c)) {
            rtn = true;
        }
        return rtn;
    }

    public boolean deleteCity(String name) {
        return cityPersistence.deleteByName(name);
    }

}
