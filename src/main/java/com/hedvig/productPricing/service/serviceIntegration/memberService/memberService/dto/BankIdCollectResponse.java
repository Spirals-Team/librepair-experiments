package com.hedvig.productPricing.service.serviceIntegration.memberService.memberService.dto;

import lombok.Value;

@Value
public class BankIdCollectResponse {
    private BankIdProgressStatus bankIdStatus;
    private String referenceToken;
    private String newMemberId;
}
