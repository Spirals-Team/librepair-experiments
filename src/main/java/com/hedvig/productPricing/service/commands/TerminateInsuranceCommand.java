package com.hedvig.productPricing.service.commands;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Value
public class TerminateInsuranceCommand {
    @TargetAggregateIdentifier
    String memberId;
}
