package com.hedvig.productPricing.service.query;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<ContractEntity, String> {
    Optional<ContractEntity> findByMemberId(String id);
}
