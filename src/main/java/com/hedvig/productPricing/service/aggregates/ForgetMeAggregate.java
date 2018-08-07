package com.hedvig.productPricing.service.aggregates;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import com.hedvig.productPricing.service.commands.ForgetMeCommand;
import com.hedvig.productPricing.service.events.ForgetMeEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aggregate
public class ForgetMeAggregate {

  private static Logger log = LoggerFactory.getLogger(ForgetMeAggregate.class);

  @AggregateIdentifier private String memberId;

  public ForgetMeAggregate() {
    log.info("Constructing ForgerMeAggregate");
  }

  @CommandHandler
  public void ForgetMeAggregate(ForgetMeCommand cmd) {
    log.info("ForgetMeAggregate");
    apply(new ForgetMeEvent(cmd.getMemberId()));
  }

  @EventSourcingHandler
  public void on(ForgetMeEvent e) {
    this.memberId = e.getMemberId();
  }
}
