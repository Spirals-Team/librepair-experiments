package com.hedvig.productPricing.service.events;

import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
public class CancellationDateSetEvent {
    String memberId;
    UUID productId;
    Instant cancellationDate;
}
