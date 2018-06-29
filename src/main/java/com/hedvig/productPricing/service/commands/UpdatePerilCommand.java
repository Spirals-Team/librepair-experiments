package com.hedvig.productPricing.service.commands;

import com.hedvig.productPricing.service.web.dto.PerilDTO;
import lombok.Value;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Value
public class UpdatePerilCommand {

	private static Logger log = LoggerFactory.getLogger(UpdatePerilCommand.class);

	@TargetAggregateIdentifier
	public String id;
    public String title;
    public String state;
    public String imageUrl;
    public String shortText;
    public String longText;
    public String category;
    public Boolean isRemovable;

    public UpdatePerilCommand(PerilDTO peril) {
        log.info("UpdatePerilCommand for peril:" + peril.id);
        this.id = peril.id;
        this.title = peril.title;
        this.state = peril.state;
        this.imageUrl = peril.imageUrl;
        this.shortText = peril.description;
        this.longText = peril.longText;
        this.category = peril.category;
        this.isRemovable = peril.isRemovable;
        log.info(this.toString());
    }
}
