package com.hedvig.productPricing.service.serviceIntegration.memberService.memberService.dto;

import lombok.Value;

@Value
public class SendOnboardedActiveTodayRequest {
    public String name;
    public String email;
}
