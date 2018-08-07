package com.hedvig.productPricing.service.serviceIntegration.memberService.memberService.dto;

import lombok.Value;

import java.time.Instant;

@Value
public class ActivationDateUpdatedRequest {
    String currentInsurer;
    Instant activationDate;
}
