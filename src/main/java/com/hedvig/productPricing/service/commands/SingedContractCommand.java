package com.hedvig.productPricing.service.commands;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.time.Instant;

@Value
public class SingedContractCommand {

    @TargetAggregateIdentifier
    private final String memberId;
    private final String referenceToken;
    private final String signature;
    private final String oscpResponse;
    private final Instant signedOn;

}
