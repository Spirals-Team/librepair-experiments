package com.hedvig.productPricing.service.web.dto;

import lombok.Value;

import java.time.Instant;

@Value
public class SetCancellationDateRequest {
    Instant inactivationDate;
}
