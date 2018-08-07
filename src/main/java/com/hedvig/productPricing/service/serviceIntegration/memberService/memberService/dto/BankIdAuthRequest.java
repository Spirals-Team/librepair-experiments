package com.hedvig.productPricing.service.serviceIntegration.memberService.memberService.dto;

import lombok.Value;

@Value
public class BankIdAuthRequest {
    private String ssn;
    private String memberId;
}
