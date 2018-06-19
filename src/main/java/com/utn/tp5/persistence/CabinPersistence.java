package com.utn.tp5.persistence;

import com.utn.tp5.model.Cabin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CabinPersistence extends JpaRepository<Cabin, Long> {
    boolean deleteByName(String name);
}
