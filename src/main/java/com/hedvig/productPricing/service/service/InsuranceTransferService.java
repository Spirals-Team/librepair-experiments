package com.hedvig.productPricing.service.service;

import com.hedvig.productPricing.service.web.dto.InsuranceCompaniesSE;

import java.time.Instant;

public interface InsuranceTransferService {


    void startTransferProcess(String memberId, Boolean insuredAtOtherCompany, InsuranceCompaniesSE currentInsurer);

    void activationDateUpdated(String memberId, String currentInsurer, Instant activationDate);
}
