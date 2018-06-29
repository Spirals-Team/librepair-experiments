package com.hedvig.productPricing.service.events;

import com.hedvig.productPricing.service.web.dto.PerilDTO;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Value
public class QuoteCalculatedEvent {
    private final UUID productId;
    private final List<PerilDTO> updatedPerils;
    private final BigDecimal newPrice;

    public QuoteCalculatedEvent(UUID memberId, List<PerilDTO> updatedPerils, BigDecimal newPrice) {
        this.productId = memberId;
        this.updatedPerils = updatedPerils;
        this.newPrice = newPrice;
    }
}
