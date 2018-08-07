package com.hedvig.productPricing.service.web.dto;

import java.util.UUID;
import lombok.Value;

import java.time.Instant;

@Value
public class SetCancellationDateRequest {
    UUID insuranceId;
    Instant inactivationDate;
}
