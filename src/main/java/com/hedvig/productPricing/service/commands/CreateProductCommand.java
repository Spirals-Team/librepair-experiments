package com.hedvig.productPricing.service.commands;

import com.hedvig.productPricing.service.aggregates.Product;
import com.hedvig.productPricing.service.web.dto.PerilDTO;
import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

@Value
public class CreateProductCommand {

	private static Logger log = LoggerFactory.getLogger(CreateProductCommand.class);

	public UUID id;

	@TargetAggregateIdentifier
	public String userId;

	public List<PerilDTO> perils;
    public double currentTotalPrice;

    private final Product.ProductTypes houseType;

    public CreateProductCommand(String userId, List<PerilDTO> perils, double currentTotalPrice,  Product.ProductTypes houseType) {
        this(UUID.randomUUID(), userId, perils, currentTotalPrice, houseType);
    }

    public CreateProductCommand(UUID productId, String userId, List<PerilDTO> perils, double currentTotalPrice, Product.ProductTypes houseType) {
        this.id = productId;
        this.userId = userId;
        this.perils = perils;
        this.currentTotalPrice = currentTotalPrice;
        this.houseType = houseType;
    }


}
