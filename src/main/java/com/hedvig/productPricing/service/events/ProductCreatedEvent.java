package com.hedvig.productPricing.service.events;

import com.hedvig.productPricing.service.aggregates.Product;
import com.hedvig.productPricing.service.web.dto.PerilDTO;
import lombok.Value;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.serialization.Revision;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Value
@Revision("1.0")
public class ProductCreatedEvent {

	@AggregateIdentifier
    public UUID id;
	public String userId;
    public List<PerilDTO> perils;
    
    public BigDecimal currentTotalPrice;
    //public ProductStates state;

    public Product.ProductTypes houseType;

    public boolean insuredAtOtherCompany;
}
