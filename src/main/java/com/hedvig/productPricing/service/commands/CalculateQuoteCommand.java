package com.hedvig.productPricing.service.commands;

import com.hedvig.productPricing.service.web.dto.PerilDTO;
import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.List;

@Value
public class CalculateQuoteCommand {
    @TargetAggregateIdentifier
    public String id;

    List<PerilDTO> perilsToUpdate;
}
