package com.hedvig.productPricing.service.events;

import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.Value;
import org.axonframework.commandhandling.model.AggregateIdentifier;

@Value
public class ProductModifiedEvent {

  @AggregateIdentifier
  private UUID insuranceIdToBeReplaced;

  private UUID InsuranceIdToReplace;
  private String memberId;
  private ZonedDateTime TerminationDate;
  private ZonedDateTime ActivationDate;
}
