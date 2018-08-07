package com.hedvig.productPricing.service.commands;

import java.util.UUID;
import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.time.Instant;

@Value
public class SetCancellationDateCommand {
    @TargetAggregateIdentifier
    String memberId;

    UUID insuranceId;
    Instant cancellationDate;
}
