package com.hedvig.productPricing.service.aggregates;

import com.hedvig.productPricing.service.commands.CreatePerilCommand;
import com.hedvig.productPricing.service.commands.UpdatePerilCommand;
import com.hedvig.productPricing.service.events.PerilCreatedEvent;
import com.hedvig.productPricing.service.events.PerilUpdatedEvent;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * This is an example Quote and should be remodeled to suit the needs of you domain.
 */
@Aggregate
public class PerilAggregate {

	private static Logger log = LoggerFactory.getLogger(PerilAggregate.class);
	public static enum perilStates {CREATED, ADD_REQUESTED, REMOVE_REQUESTED, ADD_PENDING, REMOVE_PENDING, WAITING_FOR_PAYMENT, NOT_COVERED, COVERED};

    @AggregateIdentifier
    public String id;
    public String title;
    public String state;
    public String imageUrl;
    public String shortText;
    public String longText;
    public String category;
    public Boolean isRemovable;

    public PerilAggregate(){
        log.info("Instansiating PerilAggregate");
    }

    @CommandHandler
    public PerilAggregate(CreatePerilCommand command) {
        log.info("create peril");
        apply(new PerilCreatedEvent(
        		command.getId(),
        		command.getTitle(),
        		command.getShortText(),
        		command.getLongText(),
        		command.getState(),
        		command.getImageUrl(),
        		command.getCategory(),
        		command.getIsRemovable()));
    }

    @CommandHandler
    public void PerilUpdateAggregate(UpdatePerilCommand command) {
        log.info("update peril");
        apply(new PerilUpdatedEvent(
        		command.getId(),
        		command.getTitle(),
        		command.getShortText(),
        		command.getLongText(),
        		command.getState(),
        		command.getImageUrl(),
        		command.getCategory(),
        		command.getIsRemovable()));
    }
    
    @EventSourcingHandler
    public void on(PerilUpdatedEvent e) {        
        this.title = e.getTitle();
        this.state = e.getState();
        this.imageUrl = e.getImageUrl();
        this.shortText = e.getShortText();
        this.longText = e.getLongText();
        this.category = e.getCategory();
    }

    @EventSourcingHandler
    public void on(PerilCreatedEvent e) {
        this.id = e.getId();
        this.title = e.getTitle();
        this.state = e.getState();
        this.imageUrl = e.getImageUrl();
        this.shortText = e.getShortText();
        this.longText = e.getLongText();
        this.category = e.getCategory();
    }
}
