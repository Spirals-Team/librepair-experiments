package com.hedvig.productPricing.service.commands;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.time.LocalDate;

@Value
public class ActivateInsuranceAtDate {
    @TargetAggregateIdentifier
    String id;

    LocalDate activationDate;
}
