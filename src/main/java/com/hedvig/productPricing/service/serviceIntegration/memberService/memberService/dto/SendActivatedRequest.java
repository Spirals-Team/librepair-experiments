package com.hedvig.productPricing.service.serviceIntegration.memberService.memberService.dto;

import lombok.Value;

@Value
public class SendActivatedRequest {
    public String name;
    public String email;
}

