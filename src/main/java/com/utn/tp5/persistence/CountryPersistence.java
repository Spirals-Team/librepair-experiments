package com.utn.tp5.persistence;

import com.utn.tp5.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryPersistence extends JpaRepository<Country, Long> {
    boolean deleteByName(String name);
}
