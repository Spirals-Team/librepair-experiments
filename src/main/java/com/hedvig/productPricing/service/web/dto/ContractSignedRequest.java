package com.hedvig.productPricing.service.web.dto;

import lombok.Value;

import java.time.Instant;

@Value
public class ContractSignedRequest {
    String memberId;
    String referenceToken;
    String signature;
    String oscpResponse;
    Instant signedOn;
}
