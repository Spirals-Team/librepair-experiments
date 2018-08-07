package com.hedvig.productPricing.service.service;

import com.hedvig.productPricing.service.web.dto.InsuranceCompaniesSE;

public interface InsuranceLookup {
    String extractEmailAddress(InsuranceCompaniesSE currentInsurer);
}
