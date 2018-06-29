package com.hedvig.productPricing.service.serviceIntegration.memberService.memberService.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class SendSignupRequest {
    public UUID token;
    public String email;
    public String id;
}
