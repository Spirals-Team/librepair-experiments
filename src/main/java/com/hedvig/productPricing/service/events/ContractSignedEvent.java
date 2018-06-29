package com.hedvig.productPricing.service.events;

import lombok.Value;
import org.axonframework.serialization.Revision;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Revision("1.0")
public class ContractSignedEvent {

    private String memberId;
    private UUID productId;
    private String referenceToken;
    private LocalDateTime activeFrom;
    private Instant signedOn;

}
