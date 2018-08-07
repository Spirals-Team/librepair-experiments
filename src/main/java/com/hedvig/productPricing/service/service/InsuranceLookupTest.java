package com.hedvig.productPricing.service.service;

import com.hedvig.productPricing.service.web.dto.InsuranceCompaniesSE;

public class InsuranceLookupTest implements InsuranceLookup {
    @Override
    public String extractEmailAddress(InsuranceCompaniesSE currentInsurer) {
        return String.format("johan+%s@hedvig.com", currentInsurer.name().toLowerCase());
    }
}
