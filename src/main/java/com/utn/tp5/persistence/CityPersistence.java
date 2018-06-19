package com.utn.tp5.persistence;

import com.utn.tp5.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityPersistence extends JpaRepository<City, Long> {
    boolean deleteByName(String name);
}
