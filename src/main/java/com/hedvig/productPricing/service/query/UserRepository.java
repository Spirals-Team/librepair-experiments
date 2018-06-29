package com.hedvig.productPricing.service.query;

import com.hedvig.productPricing.service.aggregates.ProductStates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findById(String s);

    @Query("from UserEntity ue where ue.insuanceActiveTo <= :time " +
        "and ue.insuranceState != :state")
    Set<UserEntity> findByInsuanceActiveToAndInsuranceStateIsNot(@Param("time") LocalDateTime time, @Param("state") ProductStates state);
}
