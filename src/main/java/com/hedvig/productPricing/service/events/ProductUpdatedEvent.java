package com.hedvig.productPricing.service.events;

import com.hedvig.productPricing.service.web.dto.PerilDTO;
import lombok.Value;
import org.axonframework.commandhandling.model.AggregateIdentifier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

@Value
public class ProductUpdatedEvent {

	@AggregateIdentifier
    public UUID id;
    public String userId;
    public ArrayList<PerilDTO> perils;
    
    public BigDecimal currentTotalPrice;
    public String status;
    
    public String statusDescription;


}
