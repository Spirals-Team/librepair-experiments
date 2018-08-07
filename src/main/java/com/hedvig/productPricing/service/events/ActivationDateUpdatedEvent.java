package com.hedvig.productPricing.service.events;

import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
public class ActivationDateUpdatedEvent {

    private String memberId;
    private UUID productId;

    private Instant activationDate;
}
