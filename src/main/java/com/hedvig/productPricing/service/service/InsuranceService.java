package com.hedvig.productPricing.service.service;

import com.hedvig.productPricing.service.query.ProductEntity;
import java.util.Optional;

public interface InsuranceService {
  Optional<ProductEntity> GetCurrentInsurance(String memberId);
}
