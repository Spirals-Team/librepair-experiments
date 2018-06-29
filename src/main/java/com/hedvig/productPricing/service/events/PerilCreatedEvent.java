package com.hedvig.productPricing.service.events;

import lombok.Value;
import org.axonframework.commandhandling.model.AggregateIdentifier;

@Value
public class PerilCreatedEvent {

	@AggregateIdentifier
    public String id;
    public String title; // Name of peril
    public String shortText;
    public String longText;
    
    public String state;
    public String imageUrl;

    public String category;
    public Boolean isRemovable;

}
