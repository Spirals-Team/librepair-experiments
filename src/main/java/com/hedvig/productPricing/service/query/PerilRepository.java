package com.hedvig.productPricing.service.query;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PerilRepository extends JpaRepository<PerilEntity, String> {
    Optional<PerilEntity> findById(String s);
    List<PerilEntity> findByCategory(String s);
    List<PerilEntity> findByIdIn(Set<String> set);
}
