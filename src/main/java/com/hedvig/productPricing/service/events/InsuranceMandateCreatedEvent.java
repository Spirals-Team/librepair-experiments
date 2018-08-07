package com.hedvig.productPricing.service.events;

import lombok.Value;

@Value
public class InsuranceMandateCreatedEvent {
    private final String memberId;
    private final byte[] mandatePdf;

    public InsuranceMandateCreatedEvent(String memberId, byte[] mandatePdf) {
        this.memberId = memberId;
        this.mandatePdf = mandatePdf;
    }
}
