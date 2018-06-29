package com.hedvig.productPricing.service.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hedvig.productPricing.pricing.PricingQuote;

@Transactional
@Repository
public interface PricingRepository extends JpaRepository<PricingQuote, Integer> {

}
