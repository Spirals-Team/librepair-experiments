package com.hedvig.productPricing.service.serviceIntegration.memberService.memberService.dto.notificationService;

import lombok.Value;

import java.time.Instant;

@Value
public class InsuranceActivationDateUpdatedRequest {
    String currentInsurer;
    Instant activationDate;
}
