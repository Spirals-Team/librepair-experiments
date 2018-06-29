package com.hedvig.productPricing.service.commands;

import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Value
public class UpdateProductCommand {

	private static Logger log = LoggerFactory.getLogger(UpdateProductCommand.class);
/*
    @TargetAggregateIdentifier
	public UUID id;
	public String userId;
    public ArrayList<PerilDTO> perils;
    public double currentTotalPrice;
    public String state;
    public String statusDescription;

	public UpdateProductCommand(String userId, InsuranceDTO product) {
        log.info("UpdateAssetCommand");
        
        this.id = product.id;
        this.userId = userId;
        // Add all peril ids for the product
        ArrayList<PerilDTO> perils = new ArrayList<PerilDTO>();
        for(CategoryDTO c : product.categories){
        	for(PerilDTO p : c.perils)perils.add(p);
        }
        
        this.perils = perils;
        this.currentTotalPrice = product.currentTotalPrice;
        this.state = product.state.name();
        this.statusDescription = product.statusDescription;
        log.info(this.toString());
        log.info(this.toString());
	}
	*/
}
