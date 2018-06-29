package com.hedvig.productPricing.service.commands;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Value
public class AcceptQuoteCommand {
    @TargetAggregateIdentifier
    public String id;

    public AcceptQuoteCommand(String id) {
        this.id = id;
    }
}
