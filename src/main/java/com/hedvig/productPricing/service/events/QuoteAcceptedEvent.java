package com.hedvig.productPricing.service.events;

import lombok.Value;

import java.util.UUID;

@Value
public class QuoteAcceptedEvent {
    private final String memberId;
    private final byte[] contract;
    private final UUID productId;

    public QuoteAcceptedEvent(String memberId, byte[] conract, UUID productId) {
        this.memberId = memberId;
        this.contract = conract;
        this.productId = productId;
    }
}
