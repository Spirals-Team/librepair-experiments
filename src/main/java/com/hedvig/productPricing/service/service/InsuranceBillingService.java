package com.hedvig.productPricing.service.service;

import com.hedvig.productPricing.pricing.PricingResult;
import com.hedvig.productPricing.service.aggregates.Product.ProductTypes;
import com.hedvig.productPricing.service.web.dto.InsuranceBillingDTO;
import com.hedvig.productPricing.service.web.dto.PerilDTO;
import com.hedvig.productPricing.service.web.dto.SafetyIncreaserType;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface InsuranceBillingService {
  PricingResult getPricingResult(
      String memberId,
      LocalDate birthDate,
      int livingSpace,
      int householdSize,
      String zipCode,
      int floor,
      List<SafetyIncreaserType> safetyIncreasers,
      ProductTypes houseType,
      boolean isStudent);

  List<PerilDTO> getPerils(ProductTypes houseType);

  List<InsuranceBillingDTO> getMonthlySubscription(YearMonth period);

  Optional<InsuranceBillingDTO> getMonthlySubscriptionByMemberId(YearMonth period, String memberId);
}
