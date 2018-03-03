package com.javapro.costs.repository.datajpa;

import com.javapro.costs.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DataJpaPurchaseRepository extends JpaRepository<Purchase, Long> {

    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT p from Purchase p WHERE p.createdDate BETWEEN :startDate AND :endDate ORDER BY p.createdDate DESC")
    List<Purchase> getBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
